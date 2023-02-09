package com.example.instagram_diana.src.repository;

import com.example.instagram_diana.src.dto.DMRoomDto;
import com.example.instagram_diana.src.dto.PopularDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DmDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<DMRoomDto> dmRoom(Long toUserId, Long loginUserId) {
        String Query ="SELECT dmId,content,fromUserId,toUserId,(select 1 where fromUserId=?) equalUserState" +
                " FROM DM AS D where (fromUserId=? or toUserId=?) and (toUserId=? or fromUserId=?);";
        Object[] Params = new Object[]{loginUserId,loginUserId,loginUserId,toUserId,toUserId};
        return this.jdbcTemplate.query(Query,
                (rs, rowNum) -> new DMRoomDto(
                        rs.getLong("dmId"),
                        rs.getString("content"),
                        rs.getLong("fromUserId"),
                        rs.getLong("toUserId"),
                        rs.getInt("equalUserState")),Params);
        }
    }

