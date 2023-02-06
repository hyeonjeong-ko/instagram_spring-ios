package com.example.instagram_diana.src.repository;

import com.example.instagram_diana.src.dto.likeStateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class likeDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public likeStateDto likeState(long postId, long loginUserId){
        //String Query = "SELECT u.userId,u.name,u.username,u.profileUrl,(select true FROM Follow where fromUserId=? AND toUserId=u.userId) followState,(?=u.userId) equalUserState FROM User u INNER JOIN Follow f ON u.userId=toUserId WHERE f.fromUserId=?";
        String Query = "SELECT exists( SELECT * FROM postLike WHERE EXISTS (SELECT * FROM postLike where (postId=? and userId=?))) AS likeState;";

        Object[] Params = new Object[]{postId,loginUserId};
        return this.jdbcTemplate.queryForObject(Query,
                (rs, rowNum) -> new likeStateDto(
                        rs.getInt("likeState")),
                Params);


    }

}
