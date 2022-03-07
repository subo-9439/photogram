package com.example.photogram.config.auth;

import com.example.photogram.domain.User;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    //    "조금이라도 역직렬화 대상 클래스 구조가 바뀌면 에러 발생해야 된다." 정도의 민감한 시스템이 아닌 이상은 클래스를 변경할 때에
    //    직접 serialVersionUID 값을 관리해주어야 클래스 변경 시 혼란을 줄일 수 있습니다.
    private final UserRepository userRepository;

    private static final long serialVersionUID = 1L;

    //1.패스워드는 알아서 체킹해준다.
    //2.리턴이 성공하면 자동으로 세션을 생성해준다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            return null;
        }else {
            return new PrincipalDetails(userEntity);
        }
    }
}
