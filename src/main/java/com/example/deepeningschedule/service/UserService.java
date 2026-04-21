package com.example.deepeningschedule.service;

import com.example.deepeningschedule.dto.user.CreateUserRequestDto;
import com.example.deepeningschedule.dto.user.CreateUserResponseDto;
import com.example.deepeningschedule.dto.user.GetAllUserResponseDto;
import com.example.deepeningschedule.entity.Schedule;
import com.example.deepeningschedule.entity.User;
import com.example.deepeningschedule.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public CreateUserResponseDto save(CreateUserRequestDto result) {

        // 1. result에서 값 꺼내기
        String username = result.getUsername();
        String email = result.getEmail();
        // 2. 꺼낸 값으로 User 객체 만들기
        User user = new User(username, email);
        // 3. userRepository에서 save() 기능으로 DB에 저장하기
        User savedUser = userRepository.save(user);
        // 4. 저장된 결과에서 값 꺼내기
        Long savedId = savedUser.getId();
        String savedUsername = savedUser.getUsername();
        String savedEmail = savedUser.getEmail();
        LocalDateTime savedCreatedAt = savedUser.getCreatedAt();
        LocalDateTime savedModifiedAt = savedUser.getModifiedAt();
        // 5. 꺼낸 값을 responseDto 만들기
        CreateUserResponseDto responseDto = new CreateUserResponseDto(
                savedId, savedUsername, savedEmail, savedCreatedAt, savedModifiedAt
        );
        // 6. dto 반환
        return responseDto;
    }

    /**
     * 전체 유저 조회
     */
    @Transactional(readOnly = true)
    public List<GetAllUserResponseDto> getAllUsers() {
        // 1. 전체 유저 List로 가져오기
        List<User> userList = userRepository.findAll();
        // 2. 스트림으로 변환
        List<GetAllUserResponseDto> result = userList.stream()
                // 3. 각 User를 DTO로 변환하기
                .map(user -> new GetAllUserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                ))

                // 4. 완성된 DTO를 List로 모은다.
                .collect(Collectors.toList());
        // 5. dto List 반환
        return result;
    }

//    // 단 건 조회
//    @Transactional(readOnly = true)
//    public void getOneUser(Long id) {
//
//
//    }


}
