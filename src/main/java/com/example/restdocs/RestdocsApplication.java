package com.example.restdocs;

import com.example.restdocs.domain.Member;
import com.example.restdocs.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class RestdocsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestdocsApplication.class, args);
    }
}
