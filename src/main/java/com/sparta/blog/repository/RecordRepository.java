package com.sparta.blog.repository;


import com.sparta.blog.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Record 주의!. -> Record가 java lang에 있다. 그래서 JpaRepository에서 사용할 때 Record에 이상한거 들어가서 오류남.
//import문
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByOrderByCreatedAtDesc();

    //userId와 똑같은 블로그 글을 찾는다.
    List<Record> findAllByUserId(Long userId);

    //현재 Record Id와 user ID일치하는 record를 가져온다.
    Optional<Record> findByIDAndUserId(Long id, Long userId);


        /*List<Product> findAllByUserId(Long userId);
        Optional<Product> findByIdAndUserId(Long id, Long userId);*/


}

