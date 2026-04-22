package com.example.deepeningschedule.service;

import com.example.deepeningschedule.dto.login.LoginRequestDto;
import com.example.deepeningschedule.dto.user.*;
import com.example.deepeningschedule.entity.User;
import com.example.deepeningschedule.repository.ScheduleRepository;
import com.example.deepeningschedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 유저 생성
     */
    @Transactional
    public CreateUserResponseDto save(CreateUserRequestDto request) {
        // 1. result에서 값 꺼내기
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        // 비밀번호 8글자 이상 검증
        if (password.length() < 8) {
            throw new RuntimeException("비밀번호는 8글자 이상이어야 합니다!");
        }
        // 2. 꺼낸 값으로 User 객체 만들기
        User user = new User(username, email, password);
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

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetOneUserResponseDto getOneUser(Long id) {
        // 1. id로 유저 가져오기
//        Optional<User> optionalUser = userRepository.findById(id);
//
////        // orElseThrow를 풀어 쓰기
////        // optionalUser에 유저가 없으면 예외처리
////        if (optionalUser.isEmpty()) {
////            throw new RuntimeException("유저를 찾을 수 없습니다.");
////            // 유저가 있으면 꺼내기
////        } else {
////            User user = optionalUser.get();
////        }
//        // 2. Optional 안에 유저가 없으면 예외처리, 있으면 꺼내기
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 1. id를 찾아 없으면 예외처리, 있으면 꺼내기
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        // 2. 꺼낸 user 객체에서 값 가져오기
        Long userId = user.getId();
        String userName = user.getUsername();
        String userEmail = user.getEmail();
        LocalDateTime userCreatedAt = user.getCreatedAt();
        LocalDateTime userModifiedAt = user.getModifiedAt();

        // 3. 가져온 값을 User -> DTO로 변환
        GetOneUserResponseDto responseDto = new GetOneUserResponseDto(
                userId, userName, userEmail,
                userCreatedAt, userModifiedAt
        );

        // 4. 만든 responseDto 반환
        return responseDto;
    }

    // 유저 수정
    @Transactional
    // DTO를 받으면
    // Service에서 그냥 user.update(result) 한 방에 끝! 풀어쓸 게 없음
    // 값을 직접 받으면 Service에서 DTO에서 값 꺼내고
    // 꺼낸 값을 update()에 넣는 과정을 풀어쓸 수 있다.
    // 뭐가 다를까..
    public UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto result) {
        // 1. id를 찾아 없으면 예외처리, 있으면 꺼내기
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다"));
        // 2. 찾은 user 업데이트
        // - result에서 값 꺼내기
        String newUserName = result.getUsername();
        String newUserEmail = result.getEmail();
        String newUserPassword = result.getNewPassword();
        String oldUserPassword = result.getPassword();

        if (!user.getPassword().equals(oldUserPassword)) {
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }

        // - 꺼낸 값 업데이트(자동 저장)
        User updatedUser = user.update(newUserName, newUserEmail, newUserPassword);
        // 3. 업테이트 한 updateUser에서 값 가져오기
        Long userId = updatedUser.getId();
        String userName = updatedUser.getUsername();
        String userEmail = updatedUser.getEmail();
        LocalDateTime userCreatedAt = updatedUser.getCreatedAt();
        LocalDateTime userModifiedAt = updatedUser.getModifiedAt();
        // 4. 가져온 값으로 DTO 만들기
        UpdateUserResponseDto responseDto = new UpdateUserResponseDto(
                userId, userName, userEmail,
                userCreatedAt, userModifiedAt
        );
        // 만든 DTO 반환
        return responseDto;
    }

    // 유저 삭제
    @Transactional
    public void delectUser(Long id) {
        // 1. id 찾는데 없으면 예외처리, 있으면 꺼내기
        User findUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        // 2. 찾은 유저 삭제
        userRepository.delete(findUser);
    }

    // login
    @Transactional(readOnly = true)
    public User login(LoginRequestDto request) {
        // 1. request 값 꺼내오기
        String email = request.getEmail();
        String password = request.getPassword();
        // 2. email로 유저 찾기
        User findUser = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("유저를 찾을 수 없습니다."));
        // 3. 찾은 유저 비밀번호 가져오기
        String findPassword = findUser.getPassword();
        // 4. 비밀번호 비교하기 (틀리면 예외처리)
        if (!password.equals(findPassword)) {
            throw new RuntimeException("비밀번호가 맞지 않습니다.");
        }
        // 6. 유저 반환
        return findUser;
    }
}
