package com.example.instagram_diana.src.user;

import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.src.model.User;
import com.example.instagram_diana.src.user.model.PostLoginReq;
import com.example.instagram_diana.src.user.model.PostUserReq;
import com.example.instagram_diana.src.user.model.PostUserRes;
import com.example.instagram_diana.src.utils.JwtService;
import com.example.instagram_diana.src.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.instagram_diana.config.BaseResponseStatus.*;

@Service
public class UserService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(JwtService jwtService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean checkEmailDuplicate(String email){
        return userRepository.existsByEmail(email);
    }

    public boolean checkPhoneDuplicate(String phone){
        return userRepository.existsByPhone(phone);
    }

    public boolean checkNameDuplicate(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Transactional
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {

        String pwd;
        try{
            //암호화 ()
            // 통일성위해 bCrypt로 로그인!
            pwd = new SHA256().encrypt(postUserReq.getPassword());//bCryptPasswordEncoder.encode(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            User user = User.builder()
                    .username(postUserReq.getUserName())
                    .name(postUserReq.getName())
                    .email(postUserReq.getEmail())
                    .phone(postUserReq.getPhone())
                    .password(postUserReq.getPassword())
                    .provider("INSTAGRAM")
                    .build();

            // 회원가입 (DB저장)

            User resUser = userRepository.save(user);
            Long userIdx = resUser.getId();

            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


        public PostUserRes logIn(PostLoginReq postLoginReq) throws BaseException {
        try {

            // 아이디: 전화번호/사용자이름/이메일 => 비밀번호 찾기 로직
            String loginInput = postLoginReq.getLoginInput();

            User user = new User();
            try {
                // 1. 이메일존재시 이메일로 DB에서 유저 찾아오기
                if (userRepository.existsByEmail(loginInput)) {
                    user = userRepository.findByEmail(loginInput);
                }
                // 2. 유저네임 존재시 유저네임으로 DB에서 유저 찾아오기
                else if (userRepository.existsByUsername(loginInput)) {
                    user = userRepository.findByUsername(loginInput);
                }
                // 3. 폰 존재시 폰으로 DB에서 유저 찾아오기
                else if (userRepository.existsByPhone(loginInput)) {
                    user = userRepository.findByPhone(loginInput);
                }

                String encryptPwd;

                if (postLoginReq.getPassword() == "없음") { //Oauth2로그인유저처리
                    Long userIdx = user.getId();
                    String jwt = jwtService.createJwt(userIdx);
                    return new PostUserRes(jwt, userIdx);
                } else {

                    try {
                        encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());

                    } catch (Exception exception) {
                        logger.error("App - logIn Provider Encrypt Error", exception);
                        throw new BaseException(PASSWORD_DECRYPTION_ERROR);
                    }

                    if (user.getPassword().equals(encryptPwd)) {
                        Long userIdx = user.getId();
                        String jwt = jwtService.createJwt(userIdx);
                        return new PostUserRes(jwt, userIdx);
                    } else {
                        throw new BaseException(FAILED_TO_LOGIN);
                    }
                }

            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_LOGIN);
            }
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }

        }

}
