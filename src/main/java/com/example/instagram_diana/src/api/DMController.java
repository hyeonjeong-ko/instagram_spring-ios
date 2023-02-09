package com.example.instagram_diana.src.api;

import com.example.instagram_diana.config.BaseException;
import com.example.instagram_diana.config.BaseResponse;
import com.example.instagram_diana.src.dto.DMDto;
import com.example.instagram_diana.src.dto.DMRoomDto;
import com.example.instagram_diana.src.service.DMService;
import com.example.instagram_diana.src.user.UserService;
import com.example.instagram_diana.src.utils.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.instagram_diana.config.BaseResponseStatus.USER_ID_NOT_EXIST;

@RestController
@RequestMapping("/app/messages")
public class DMController {

    private final JwtService jwtService;
    private final UserService userService;
    private final DMService dmService;

    public DMController(JwtService jwtService, UserService userService, DMService dmService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.dmService = dmService;
    }

    @PostMapping("")
    public BaseResponse<?> dmSend(@RequestBody DMDto dmDto){
        try{
            if(!userService.checkUserExist(dmDto.getToUserId())){
                return new BaseResponse<>(USER_ID_NOT_EXIST);
            };

            Long loginUserId = jwtService.getUserIdx();

            dmService.dmSend(dmDto,loginUserId);

            return new BaseResponse<>("메시지 요청 성공");
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{toUserId}")
    public BaseResponse<?> dmRoom(@PathVariable Long toUserId){
        try{
            if(!userService.checkUserExist(toUserId)){
                return new BaseResponse<>(USER_ID_NOT_EXIST);
            };

            Long loginUserId = jwtService.getUserIdx();

            List<DMRoomDto> dtos=dmService.dmRoom(toUserId,loginUserId);

            return new BaseResponse<>("메시지방 내용 요청 성공",dtos);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @DeleteMapping("/{messageId}")
    public BaseResponse<?> dmDelete(@PathVariable("messageId") Long mesageId){

        dmService.dmDelete(mesageId);
        return new BaseResponse<>("메시지 삭제 요청 성공");
    }

}
