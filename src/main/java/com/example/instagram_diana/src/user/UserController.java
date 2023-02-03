package com.example.instagram_diana.src.user;


import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.config.BaseResponse;
import com.example.instagram_diana.config.auth.PrincipalDetails;
import com.example.instagram_diana.src.handler.CustomValidationException;
import com.example.instagram_diana.src.model.User;
import com.example.instagram_diana.src.user.model.PatchUserReq;
import com.example.instagram_diana.src.user.model.PostLoginReq;
import com.example.instagram_diana.src.user.model.PostUserReq;
import com.example.instagram_diana.src.user.model.PostUserRes;
import com.example.instagram_diana.src.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.example.instagram_diana.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final UserService userService;

    @Autowired
    private final JwtService jwtService;




    public UserController(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }



    // 토큰받기 로직!!!!(세션 필터단 oauth2 로그인 + 일반로그인 + JWT 토큰 구현)
    @GetMapping ("/success")
    public BaseResponse<PrincipalDetails> logInSuccess(Authentication authentication)
    {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails);
        System.out.println("안 . 뇽? 반 가 워 나 는 테 스 트 다.");

        return new BaseResponse<>("로그인을 성공했습니다.",principalDetails);
        //return principalDetails;


    }


    @GetMapping ("/{username}")
    public BaseResponse<PostUserRes> Oauth2Login(@PathVariable("username") String username)
    {
        try{

            PostLoginReq postUserReq = new PostLoginReq(username,"없음");
            PostUserRes postLoginRes = userService.logIn(postUserReq);
            return new BaseResponse<>(postLoginRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }



    /**
     * 일반 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */

    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody @Validated PostUserReq postUserReq,
                                                BindingResult bindingResult) throws BaseException {

        if(bindingResult.hasErrors()){ // 유효성 검사
            getPostUserResBaseResponse(bindingResult);
        }else {

            // username,email,phone 중복 검사
            if(userService.checkNameDuplicate(postUserReq.getUserName())==true && postUserReq.getUserName()!=null){
                return new BaseResponse<>(POST_USERS_EXISTS_NAME);
            }

            // 이메일 값이존재하고, 값이 중복일때 에러
            if(userService.checkEmailDuplicate(postUserReq.getEmail())==true && postUserReq.getEmail()!=null){
                return new BaseResponse<>(POST_USERS_EXISTS_EMAIL);
            }

            if(userService.checkPhoneDuplicate(postUserReq.getPhone())==true && postUserReq.getPhone()!=null){
                return new BaseResponse<>(POST_USERS_EXISTS_PHONE);
            }


            // email, phone 둘 다 없을시 에러
            if (postUserReq.getEmail() == null && postUserReq.getPhone() == null) {
                return new BaseResponse<>(POST_USERS_EMPTY_ID);
            }

            // 회원가입
            try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>("회원가입을 성공했습니다.",postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
        }
        return null;
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostUserRes> logIn(@RequestBody @Validated PostLoginReq postLoginReq,
                                           BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){ // 유효성 검사
            return getPostUserResBaseResponse(bindingResult);
        }else{

            try{
                PostUserRes postLoginRes = userService.logIn(postLoginReq);
                if (postLoginRes==null){
                    return new BaseResponse<>(FAILED_TO_LOGIN);
                }
                return new BaseResponse<>(postLoginRes);
            } catch (BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
        }

    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<PatchUserReq> modifyUserName(@PathVariable("userIdx") long userIdx, @RequestBody PatchUserReq patchUserReq){
        try {
            // jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();

            // userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // 유저가 보낸 수정 데이터 형식 검증

            // 유저네임이 빈값이면 에러
            if(patchUserReq.getUserName()==null) return new BaseResponse<>(PATCH_USERS_NULL_NAME);

            // 같다면 유저정보 변경
            userService.modifyUserInfo(userIdx,patchUserReq);
            return new BaseResponse<>("회원 정보 수정에 성공했습니다.",patchUserReq);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 유효성 검사 함수
    private BaseResponse<PostUserRes> getPostUserResBaseResponse(BindingResult bindingResult) {
        Map<String,String> errorMap = new HashMap<>();

        for(FieldError error:bindingResult.getFieldErrors()){
            errorMap.put(error.getField(),error.getDefaultMessage());
            System.out.println(error.getDefaultMessage());
        }
        throw new CustomValidationException("유효성 검사 실패",errorMap);
    }

}