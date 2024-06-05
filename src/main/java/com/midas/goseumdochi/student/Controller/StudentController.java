package com.midas.goseumdochi.student.Controller;
import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Repository.StudentRepository;
import com.midas.goseumdochi.student.Service.FileStorageService;
import com.midas.goseumdochi.student.Service.StudentService;
import com.midas.goseumdochi.student.entity.StudentEntity;
import com.midas.goseumdochi.teacher.dto.LectureDTO;
import com.midas.goseumdochi.teacher.service.LectureService;
import com.midas.goseumdochi.util.Dto.MailDTO;
import com.midas.goseumdochi.util.Service.MailService;
import com.midas.goseumdochi.util.ai.EncDecService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FileStorageService fileStorageService;
    private final MailService mailService;
    private final LectureService lectureService;
    private final StudentRepository studentRepository;
    private final EncDecService encDecService;

    // 회원가입 페이지 폼 작성 데이터 받기
    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody StudentDTO studentDTO) {
        System.out.println("Received studentDTO: " + studentDTO);
        // 전화번호 형식 - 있는 거에서 -> 없는 것으로 (숫자 11자)
        if (!studentDTO.getStudentPhoneNumber().matches("^\\d{11}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전화번호 형식이 올바르지 않습니다.");
        }

        int joinResult = studentService.join(studentDTO, studentDTO.getConfirmPassword());
        // 결과에 따른 응답 처리
        if (joinResult == -4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이메일 또는 생년월일 형식이 잘못되었습니다.");
        } else if (joinResult == -3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 필드가 누락되었습니다.");
        } else if (joinResult == -2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        } else if (joinResult == -1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("아이디가 이미 존재합니다.");
        } else if (joinResult < 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패!!");
        }
        return ResponseEntity.ok("회원가입 성공. 로그인해주세요.");
    }

    // 로그인 폼 입력 데이터 받고 로직
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentDTO studentDTO, HttpSession session) {
        StudentDTO loginResult = studentService.login(studentDTO);
        if (loginResult != null) {
            session.setAttribute("loginstudentId", loginResult.getStudentId());
            session.setAttribute("loginId", loginResult.getId());
            session.setAttribute("studentName", loginResult.getStudentName());
            return ResponseEntity.ok("로그인 성공.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패!!");
    }

    // 아이디 찾기 처리
    @PostMapping("/findStudentId")
    public ResponseEntity<?> findStudentId(@RequestBody StudentDTO studentDTO) {
        Optional<String> studentId = studentService.findStudentIdByStudentNameAndPhoneNumber(studentDTO.getStudentName(), studentDTO.getStudentPhoneNumber());
        return studentId.map(id -> ResponseEntity.ok("아이디는 " + id + " 입니다."))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보와 일치하는 사용자가 없습니다."));
    }

    // 비밀번호 찾기 - 임시 비밀번호 전송
    @PostMapping("/findStudentPassword")
    public ResponseEntity<?> findStudentPassword(@RequestBody StudentDTO studentDTO) {
        Optional<StudentDTO> studentOptional = studentService.findStudentByStudentIdAndStudentNameAndPhoneNumber(studentDTO.getStudentId(), studentDTO.getStudentName(), studentDTO.getStudentPhoneNumber());
        if (!studentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보와 일치하는 사용자가 없습니다.");
        }
        MailDTO dto = mailService.createMailAndChangePassword(studentOptional.get().getStudentEmail(), studentOptional.get().getId());
        mailService.mailSend(dto);
        return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
    }

    // 마이페이지 사용자 정보 조회
    @GetMapping("/info")
    public ResponseEntity<?> getStudentInfo(HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        StudentDTO studentDTO = studentService.findStudentById(studentId);
        if (studentDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("학생 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(studentDTO);
    }

    // 사용자 정보 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateStudent(@RequestBody StudentDTO studentDTO, HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        studentDTO.setId(studentId);
        int updateResult = studentService.updateStudent(studentDTO);
        if (updateResult == 1) {
            return ResponseEntity.ok("정보 수정 성공.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("정보 수정에 실패하였습니다.");
    }

    // 프로필 사진
    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택되지 않았습니다.");
        }
        try {
            String imageUrl = fileStorageService.uploadFile(file, "profile_pictures"); // 폴더 경로 지정
            StudentDTO studentDTO = studentService.findStudentById(studentId);

            if (studentDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("학생 정보를 찾을 수 없습니다.");
            }

            studentDTO.setProfilePictureUrl(imageUrl); // 업로드된 파일 URL 저장
            studentService.updateStudent(studentDTO); // 업데이트 서비스 호출
            return ResponseEntity.ok("프로필 사진이 업데이트 되었습니다.");

        } catch (IOException e) { // 파일 업로드중 예외 캐치
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }

    // 비밀번호 변경
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody StudentDTO studentDTO, HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        if (studentDTO.getCurrentPassword() == null || studentDTO.getNewPassword() == null || studentDTO.getConfirmNewPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모든 필드를 입력해 주세요.");
        }

        // DB에서 사용자 정보 가져오기
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 사용자 ID입니다."));

        // 현재 비밀번호가 일치하는지 확인 (복호화 후 비교)
        String decryptedPassword = encDecService.decrypt(studentEntity.getStudentPassword());
        if (!studentDTO.getCurrentPassword().equals(decryptedPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        }

        if (studentDTO.getCurrentPassword().equals(studentDTO.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
        }

        if (!studentDTO.getNewPassword().equals(studentDTO.getConfirmNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 비밀번호 확인이 일치하지 않습니다.");
        }

        studentService.updateStudentPassword(studentId, studentDTO.getNewPassword());
        session.invalidate(); // 세션 무효화하여 로그아웃 처리
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    // 로그아웃 기능
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화하여 로그아웃 처리
        return ResponseEntity.ok("로그아웃 성공");
    }


    // 학생이 수강하는 강의 리스트 출력 (강의시간 포함)
    @GetMapping("/{studentId}/lecture")
    public ResponseEntity<?> showLectureAndTimeList(@PathVariable Long studentId) {
        List<LectureDTO> lectureDTOList = lectureService.getLectureAndTimeListByStudent(studentId);
        return ResponseEntity.ok(lectureDTOList);
    }
}