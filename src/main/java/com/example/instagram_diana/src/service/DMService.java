package com.example.instagram_diana.src.service;


import com.example.instagram_diana.src.dto.DMDto;
import com.example.instagram_diana.src.dto.DMRoomDto;
import com.example.instagram_diana.src.model.Dm;
import com.example.instagram_diana.src.model.User;
import com.example.instagram_diana.src.repository.DmDao;
import com.example.instagram_diana.src.repository.DmRepository;
import com.example.instagram_diana.src.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DMService {

    private final UserService userService;
    private final DmRepository dmRepository;

    private final DmDao dmDao;


    @Autowired
    public DMService(UserService userService, DmRepository dmRepository, DmDao dmDao) {
        this.userService = userService;
        this.dmRepository = dmRepository;
        this.dmDao = dmDao;
    }

    @Transactional
    public void dmSend(DMDto dmDto, Long loginUserId) {
        User fromUser = userService.findUserById(loginUserId);
        User toUser = userService.findUserById(dmDto.getToUserId());

        Dm dm = Dm.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .content(dmDto.getContent())
                .build();


        dmRepository.save(dm);
    }

    public List<DMRoomDto> dmRoom(Long toUserId, Long loginUserId) {

        return dmDao.dmRoom(toUserId,loginUserId);
    }

    @Transactional
    public void dmDelete(Long messageId) {
        dmRepository.dmDelete(messageId);
    }
}
