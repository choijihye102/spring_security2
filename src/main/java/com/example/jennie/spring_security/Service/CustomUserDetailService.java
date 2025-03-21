package com.example.jennie.spring_security.Service;

import com.example.jennie.spring_security.domain.User;
import com.example.jennie.spring_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // JPA를 이용해서 사용자 정보를 데이터베이스에서 조회하고
    // 그 결과
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        // JPA와 MariaDB를 이용해서 사용자 정보를 확인.
        User user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 인증에 성공하면 userdetails 객체 생성하고 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserid())
                .password(user.getPasswd())
                .roles(user.getRole())
                .build();

    }

}
