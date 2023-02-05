package com.example.instagram_diana.src.api;

import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.config.BaseResponse;
import com.example.instagram_diana.src.dto.PostUploadDto;
import com.example.instagram_diana.src.model.PostMedia;
import com.example.instagram_diana.src.service.LikeService;
import com.example.instagram_diana.src.service.PostService;
import com.example.instagram_diana.src.testS3.FileDto;
import com.example.instagram_diana.src.testS3.FileService;
import com.example.instagram_diana.src.testS3.S3Service;
import com.example.instagram_diana.src.user.UserService;
import com.example.instagram_diana.src.utils.JwtService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.example.instagram_diana.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/users")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final S3Service s3Service;
    private final JwtService jwtService;
    private final LikeService likeService;

    @Autowired
    public PostController(UserService userService, PostService postService, S3Service s3Service, JwtService jwtService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.s3Service = s3Service;
        this.jwtService = jwtService;
        this.likeService = likeService;
    }


    //@PathVariable("userId") long PageUserId X. form을 주소단에서 받을 수 없음.
    //MultipartFile post-upload.
    @PostMapping("/posts")
    public BaseResponse<?> multiFilePostUpload(@RequestParam("userId") Long PageUserId,
                                      @RequestParam("content") String content,
                                      @RequestParam("multiFile") List<MultipartFile> multipartFiles) throws IOException {
        System.out.println("멀티파일 업로드 테스트컨트롤러입니다.");
        System.out.println("인풋값 받나 테스트: "+content+" and "+PageUserId);

        try{
            // 유저번호가 없으면 에러
            if (!userService.checkUserExist(PageUserId)){
                return new BaseResponse<>(USER_ID_NOT_EXIST);
            }

            // posts를 올릴 수 있는지 권한 확인(userId와 jwtId일치해야함)

            //--인증부분!!!!!!!!!! --------------------------------
//            Long LoginId = jwtService.getUserIdx();
//
//            if(PageUserId!=LoginId){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            Long LoginId=PageUserId;

            //--인증부분!!!!!!!!!! --------------------------------

            if (multipartFiles == null) {   // 첨부파일 없을 시 에러.
                throw new BaseException(POST_FILE_EMPTY);
            }else{
                // s3 서버에 이미지 저장
                List<String> imgUrlList = s3Service.upload(multipartFiles);
                System.out.println("Controller-imgUrlList: "+imgUrlList);

                // 서비스에 전달할 게시물 dto생성
                PostUploadDto postUploadDto = new PostUploadDto();
                postUploadDto.setUserId(LoginId);
                postUploadDto.setContent(content);
                postUploadDto.setImgUrlList(imgUrlList);

                postService.postUpload(postUploadDto,LoginId);
            }

            return new BaseResponse<>("포스트 업로드요청에 성공했습니다.");

    }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/like/{postId}")
    public BaseResponse<?> likes(@PathVariable long postId){

        try{

            // 주소의 유저번호가 없으면 에러
            if (!postService.checkPostExist(postId)){
                return new BaseResponse<>(POST_ID_NOT_EXISTS);
            }

            Long loginUserId = jwtService.getUserIdx();

            // 게시물 작성자와 좋아요 유저가 같으면 에러
            if (postService.getPostWriter(postId)==loginUserId) {
                return new BaseResponse<>(POST_CANNOT_MYSELF);

            }

            likeService.Like(postId,loginUserId);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        return new BaseResponse<>("좋아요 요청에 성공했습니다.");
    }

    @PostMapping("/unlike/{postId}")
    public BaseResponse<?> unlikes(@PathVariable long postId){

        try{
            // 주소의 유저번호가 없으면 에러
            if (!postService.checkPostExist(postId)){
                return new BaseResponse<>(POST_ID_NOT_EXISTS);
            }

            Long loginUserId = jwtService.getUserIdx();

            likeService.unLike(postId,loginUserId);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        return new BaseResponse<>("좋아요 취소 요청에 성공했습니다.");
    }

    //public BaseResponse

}

