package com.midas.goseumdochi.student.Controller;
import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Service.FileStorageService;
import com.midas.goseumdochi.student.Service.StudentService;
import com.midas.goseumdochi.util.Dto.MailDTO;
import com.midas.goseumdochi.util.Service.MailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final FileStorageService fileStorageService;
    private final MailService mailService;

    // 회원가입 페이지 폼 작성 데이터 받기
    @PostMapping("/signup")
    public ResponseEntity<?> join(@ModelAttribute StudentDTO studentDTO, @RequestParam("confirmPassword") String confirmPassword) {
        if (!studentDTO.getStudentPhoneNumber().matches("^\\d{3}-\\d{4}-\\d{4}$")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전화번호 형식이 올바르지 않습니다.");
        }

        int joinResult = studentService.join(studentDTO, confirmPassword);
        if (joinResult < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패!!");
        }
        return ResponseEntity.ok("회원가입 성공. 로그인해주세요.");
    }

    // 로그인폼 입력 데이터 받고 로직
    @PostMapping("/login")
    public ResponseEntity<?> login(@ModelAttribute StudentDTO studentDTO, HttpSession session) {
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
    public ResponseEntity<?> findStudentId(@RequestParam("studentName") String studentName,
                                           @RequestParam("studentPhoneNumber") String studentPhoneNumber) {
        Optional<String> studentId = studentService.findStudentIdByStudentNameAndPhoneNumber(studentName, studentPhoneNumber);
        return studentId.map(id -> ResponseEntity.ok("아이디는 " + id + " 입니다."))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보와 일치하는 사용자가 없습니다."));
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findStudentPassword")
    public ResponseEntity<?> findStudentPassword(@RequestParam("studentId") String studentId,
                                                 @RequestParam("studentName") String studentName,
                                                 @RequestParam("studentPhoneNumber") String studentPhoneNumber) {
        Optional<StudentDTO> studentOptional = studentService.findStudentByStudentIdAndStudentNameAndPhoneNumber(studentId, studentName, studentPhoneNumber);
        if (!studentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보와 일치하는 사용자가 없습니다.");
        }
        MailDTO dto = mailService.createMailAndChangePassword(studentOptional.get().getStudentEmail(), studentOptional.get().getId());
        mailService.mailSend(dto);
        return ResponseEntity.ok("임시 비밀번호가 이메일로 발송되었습니다.");
    }

    // 사용자 정보 수정
    @PostMapping("/update")
    public ResponseEntity<?> updateStudent(@ModelAttribute StudentDTO studentDTO, HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId"); // 로그인 세션에서 사용자 ID 가져오기
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
            String imageUrl = fileStorageService.uploadFile(file); // 파일 업로드 서비스 호출
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
    public ResponseEntity<?> changePassword(@RequestParam("currentPassword") String currentPassword,
                                            @RequestParam("newPassword") String newPassword,
                                            @RequestParam("confirmNewPassword") String confirmNewPassword,
                                            HttpSession session) {
        Long studentId = (Long) session.getAttribute("loginId");
        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        StudentDTO student = studentService.findStudentById(studentId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 없습니다.");
        }

        if (!student.getStudentPassword().equals(currentPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        }

        if (currentPassword.equals(newPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 비밀번호는 현재 비밀번호와 달라야 합니다.");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("새 비밀번호 확인이 일치하지 않습니다.");
        }

        studentService.updateStudentPassword(studentId, newPassword);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

}
