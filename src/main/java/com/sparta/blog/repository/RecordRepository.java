package com.sparta.blog.repository;


import com.sparta.blog.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Record 주의!. -> Record가 java lang에 있다. 그래서 JpaRepository에서 사용할 때 Record에 이상한거 들어가서 오류남.
//import문
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByOrderByCreatedAtDesc();

}

