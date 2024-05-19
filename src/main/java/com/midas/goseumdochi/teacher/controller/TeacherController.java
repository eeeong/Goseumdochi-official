package com.midas.goseumdochi.teacher.controller;

import com.midas.goseumdochi.teacher.dto.TeacherDTO;
import com.midas.goseumdochi.teacher.service.TeacherService;
import com.midas.goseumdochi.util.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.midas.goseumdochi.teacher.dto.LectureMaterialDTO;
import com.midas.goseumdochi.teacher.service.LectureMaterialService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    final private TeacherService teacherService;
    final private MailService mailService;
    private final LectureMaterialService lectureMaterialService;

    @PostMapping("/regist")
    public ResponseEntity<?> registTeacher(@RequestBody TeacherDTO inputTeacherDTO) { // id, loginid랑 password 빼고 전달받음
        TeacherDTO teacherDTO = teacherService.setLoginidAndPassword(inputTeacherDTO);
        teacherService.regist(teacherDTO);

        // 선생 등록 성공
        mailService.mailSend(teacherService.getMailDTOForRegist(teacherDTO)); // 원장에게 메일 전송

        return ResponseEntity.ok(teacherDTO); // 선생 dto 리턴
    }


    // 새로운 강의 자료 생성
    @PostMapping("/lecture-material/new")
    public ResponseEntity<?> createNewMaterial(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uploadDir = "uploads/";

        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());

        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // 현재 로그인된 사용자의 이름을 가져옴

        // TeacherDTO에서 현재 로그인된 사용자 이름을 가져옴
        TeacherDTO currentTeacher = teacherService.findByLoginid(currentUserName);

        // LectureMaterialDTO 객체 생성 및 설정
        LectureMaterialDTO lectureMaterialDTO = new LectureMaterialDTO();
        lectureMaterialDTO.setTitle(title);
        lectureMaterialDTO.setContent(content);
        lectureMaterialDTO.setAuthor(currentTeacher.getName()); // 현재 로그인된 사용자의 이름을 설정
        lectureMaterialDTO.setAttachmentPath(filePath.toString());
        // 강의 자료 저장
        lectureMaterialService.saveLectureMaterial(lectureMaterialDTO);

        return ResponseEntity.ok("새로운 강의 자료 생성 완료");
    }

    // 모든 강의 자료 목록 조회
    @GetMapping("/lecture-material/list")
    public ResponseEntity<List<LectureMaterialDTO>> getAllMaterials() {
        List<LectureMaterialDTO> materials = lectureMaterialService.getAllLectureMaterials();
        return ResponseEntity.ok(materials);
    }

    // 특정 강의 자료 조회
    @GetMapping("/lecture-material/{id}")
    public ResponseEntity<LectureMaterialDTO> getMaterialById(@PathVariable Long id) {
        LectureMaterialDTO material = lectureMaterialService.getLectureMaterialById(id);
        return ResponseEntity.ok(material);
    }

    // 강의 자료 업데이트
    @PutMapping("/lecture-material/{id}")
    public ResponseEntity<?> updateMaterial(@PathVariable Long id,
                                            @RequestParam("title") String title,
                                            @RequestParam("content") String content,
                                            @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uploadDir = "uploads/";

        // 디렉토리가 없으면 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());

        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // 현재 로그인된 사용자의 이름을 가져옴

        // TeacherDTO에서 현재 로그인된 사용자 이름을 가져옴
        TeacherDTO currentTeacher = teacherService.findByLoginid(currentUserName);

        // LectureMaterialDTO 객체 생성 및 설정
        LectureMaterialDTO lectureMaterialDTO = new LectureMaterialDTO();
        lectureMaterialDTO.setTitle(title);
        lectureMaterialDTO.setContent(content);
        lectureMaterialDTO.setAuthor(currentTeacher.getName()); // 현재 로그인된 사용자의 이름을 설정
        lectureMaterialDTO.setAttachmentPath(filePath.toString());
        // 강의 자료 업데이트
        lectureMaterialService.updateLectureMaterial(id, lectureMaterialDTO);

        return ResponseEntity.ok("강의자료 성공적으로 업로드");
    }
}
