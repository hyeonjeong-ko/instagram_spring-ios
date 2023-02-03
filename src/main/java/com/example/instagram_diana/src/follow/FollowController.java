package com.example.instagram_diana.src.follow;

import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.config.BaseResponse;
import com.example.instagram_diana.src.handler.CustomResponseDto;
import com.example.instagram_diana.src.service.FollowService;
import com.example.instagram_diana.src.user.model.PostUserRes;
import com.example.instagram_diana.src.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.instagram_diana.config.BaseResponseStatus.FAILED_TO_LOGIN;

@RequestMapping("/app")
@RestController
public class FollowController {


    private final FollowService followService;


    private final JwtService jwtService;

    @Autowired
    public FollowController(FollowService followService, JwtService jwtService) {
        this.followService = followService;
        this.jwtService = jwtService;
    }


    @PostMapping("/follow/{toUserId}")
    public ResponseEntity<?> FollowToggle(@PathVariable("toUserId") long toUserId) throws BaseException {
//
//        if (fromUserId==null){
//            return new ResponseEntity<>(new CustomResponseDto<>(-1,"인증정보가 유효하지 않습니다.",null), HttpStatus.OK);
//        }

        Long fromUserId = jwtService.getUserIdx();

        String returnMessage = (followService.checkFollow(fromUserId,toUserId)==1)?
                "id:"+ fromUserId + "님이 " + "id:" + toUserId + "님을 구독취소했습니다.":
                "id:" + fromUserId + "님이 " + "id:"+toUserId + "님을 구독했습니다.";

        if(followService.checkFollow(fromUserId,toUserId)==1){
            followService.unFollow(fromUserId,toUserId);
        }else{
            followService.follow(fromUserId,toUserId);

        }

        return new ResponseEntity<>(new CustomResponseDto<>(1,returnMessage,null), HttpStatus.OK);
        //return null;
    }


}
