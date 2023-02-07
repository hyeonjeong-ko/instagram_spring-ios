package com.example.instagram_diana.src.repository;


import com.example.instagram_diana.src.dto.DayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DayDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DayDto monthOfDay(long postId){
        String Query = "SELECT MONTH(updatedAt) as month,week(updatedAt) as day FROM Post WHERE postId=?;";
        return this.jdbcTemplate.queryForObject(Query,
                (rs, rowNum) -> new DayDto(
                        rs.getInt("month"),
                        rs.getInt("day")),
                postId);

    }
}
