package com.midas.goseumdochi.teacher.controller;

import com.midas.goseumdochi.student.Dto.StudentDTO;
import com.midas.goseumdochi.student.Service.FileStorageService;
import com.midas.goseumdochi.student.Service.RegistLectureService;
import com.midas.goseumdochi.teacher.dto.*;
import com.midas.goseumdochi.teacher.service.*;
import com.midas.goseumdochi.util.Service.MailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final MailService mailService;
    private final LectureMaterialService lectureMaterialService;
    private final AssignmentService assignmentService;
    private final LectureService lectureService;
    private final RegistLectureService registLectureService;
    private final LectureInfoService lectureInfoService;
    private final SubjectNoticeService subjectNoticeService;

    @Autowired
    private FileStorageService fileStorageService;

    // 선생님의 정보를 가져오는 엔드포인트
    @GetMapping("/info")
    public ResponseEntity<?> getTeacherInfo(HttpSession session) {
        Long id = (Long) session.getAttribute("loginId");
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        TeacherDTO teacherDTO = teacherService.findById(id);
        if (teacherDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("선생님 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(teacherDTO);
    }

    // 선생 등록
    @PostMapping("/regist")
    public ResponseEntity<?> registTeacher(@RequestBody TeacherDTO inputTeacherDTO) {
        inputTeacherDTO = teacherService.setLoginidAndPassword(inputTeacherDTO);
        TeacherDTO registTeacherDTO = teacherService.regist(inputTeacherDTO);

        // 선생 등록 성공, 원장에게 메일 전송
        mailService.mailSend(teacherService.getMailDTOForRegist(registTeacherDTO));

        return ResponseEntity.ok(registTeacherDTO); // 선생 dto 리턴
    }

    // 강의 등록
    @PostMapping("/{teacherId}/lecture/regist")
    public ResponseEntity<?> registLecture(@PathVariable Long teacherId, @RequestBody LectureDTO lectureDTO) {
        lectureDTO.setTeacherId(teacherId);
        lectureService.regist(lectureDTO);

        return ResponseEntity.ok(lectureDTO);
    }

    // 선생의 모든 강의+시간 조희
    @GetMapping("/{teacherId}/lecture")
    public ResponseEntity<?> showLectureAndTimeList(@PathVariable Long teacherId) {
        List<LectureDTO> lectureDTOList = lectureService.getLectureAndTimeListByTeacher(teacherId);
        return ResponseEntity.ok(lectureDTOList);
    }

    // 검색어로 선생 강의+시간 조희
    @GetMapping("/{teacherId}/lecture/search")
    public ResponseEntity<?> searchLectureAndTimeList(@PathVariable Long teacherId, @RequestParam String word) {
        List<LectureDTO> lectureDTOList = lectureService.searchLectureAndTimeListByTeacher(teacherId, word);
        return ResponseEntity.ok(lectureDTOList);
    }

    // 강의를 수강하는 학생 리스트 출력
    @GetMapping("lecture/{lectureId}/student/exist")
    public ResponseEntity<?> showStudentExist(@PathVariable Long lectureId) {
        List<StudentDTO> studentDTOList = registLectureService.getExistStudentDTOList(lectureId);
        return ResponseEntity.ok(studentDTOList);
    }

    // 강의를 수강하지 않는 학생 리스트 출력
    @GetMapping("lecture/{lectureId}/student/non-exist")
    public ResponseEntity<?> showStudentNonExist(@PathVariable Long lectureId) {
        List<StudentDTO> studentDTOList = registLectureService.getNonExistStudentDTOList(lectureId);
        return ResponseEntity.ok(studentDTOList);
    }

    // 강의에 학생 등록
    @PostMapping("/lecture/{lectureId}/student/regist")
    public ResponseEntity<?> registStudentToLecture(@PathVariable Long lectureId, @RequestParam Long studentId) {
        registLectureService.regist(lectureId, studentId);
        return ResponseEntity.ok("강의 학생 등록 성공");
    }

    // 강의에 등록된 학생 삭제
    @PostMapping("lecture/{lectureId}/student/delete")
    public ResponseEntity<?> deleteStudentToLecture(@PathVariable Long lectureId, @RequestParam Long studentId) {
        if (registLectureService.delete(lectureId, studentId) == false)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("수강 삭제 실패");
        return ResponseEntity.ok("수강 삭제 성공");
    }

    // 강의 정보 조회
    @GetMapping("/lecture/{id}")
    public ResponseEntity<LectureDTO> getLectureInfo(@PathVariable Long id) {
        LectureDTO lectureDTO = lectureInfoService.getLectureInfo(id);
        return ResponseEntity.ok(lectureDTO);
    }

    // 강의 정보 업데이트
    @PutMapping("/lecture/update")
    public ResponseEntity<?> updateLectureInfo(@RequestBody LectureDTO lectureDTO) {
        lectureInfoService.updateLectureInfo(lectureDTO);
        return ResponseEntity.ok("강의 정보가 성공적으로 업데이트되었습니다.");
    }

    // 새로운 강의 자료 생성
    @PostMapping("/lecture/{lectureId}/lecture-material/new")
    public ResponseEntity<?> createNewMaterial(@PathVariable Long lectureId,
                                               @RequestPart("material") LectureMaterialDTO lectureMaterialDTO,
                                               @RequestPart("file") MultipartFile file,
                                               @RequestParam("id") Long id) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택되지 않았습니다.");
        }
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = fileStorageService.uploadFile(file, "lecture_materials");
        }

        // ID로 선생님 정보를 찾기
        TeacherDTO currentTeacher = teacherService.findById(id);

        lectureMaterialDTO.setAuthor(currentTeacher.getName());
        lectureMaterialDTO.setAttachmentPath(imageUrl);
        lectureMaterialDTO.setLectureId(lectureId);

        lectureMaterialService.saveLectureMaterial(lectureMaterialDTO);

        return ResponseEntity.ok("새로운 강의 자료가 생성되었습니다.");
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
                                            @RequestPart("material") LectureMaterialDTO lectureMaterialDTO,
                                            @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택되지 않았습니다.");
        }
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = fileStorageService.uploadFile(file, "lecture_materials");
        }

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String currentUserName = authentication.getName();
        TeacherDTO currentTeacher = teacherService.findById(id);

        lectureMaterialDTO.setAuthor(currentTeacher.getName());
        lectureMaterialDTO.setAttachmentPath(imageUrl);
        lectureMaterialService.updateLectureMaterial(id, lectureMaterialDTO);

        return ResponseEntity.ok("강의 자료가 성공적으로 업데이트되었습니다.");
    }

    @GetMapping("/lecture/{lectureId}/materials")
    public ResponseEntity<List<LectureMaterialDTO>> getMaterialsByLecture(@PathVariable Long lectureId) {
        List<LectureMaterialDTO> materials = lectureMaterialService.getMaterialsByLectureId(lectureId);
        return ResponseEntity.ok(materials);
    }

    // 수업자료 삭제
    @DeleteMapping("/lecture-material/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Long id) {
        lectureMaterialService.deleteLectureMaterial(id);
        return ResponseEntity.ok("수업 자료가 성공적으로 삭제되었습니다.");
    }

    // 과제 추가
    @PostMapping("/lecture/{lectureId}/assignment/new")
    public ResponseEntity<?> createNewAssignment(@PathVariable Long lectureId,
                                                 @RequestPart("assignment") AssignmentDTO assignmentDTO,
                                                 @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택되지 않았습니다.");
        }
        String fileUrl = fileStorageService.uploadFile(file, "assignments");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        TeacherDTO currentTeacher = teacherService.findByLoginid(currentUserName);

        assignmentDTO.setAuthor(currentTeacher.getName());
        assignmentDTO.setAttachmentPath(fileUrl);
        assignmentDTO.setCreatedAt(LocalDateTime.now());
        assignmentDTO.setLectureId(lectureId); // fk

        assignmentService.saveAssignment(assignmentDTO);
        return ResponseEntity.ok("새로운 과제가 생성되었습니다.");
    }

    // 과제 목록 조회
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    // 특정 강의 자료 조회
    @GetMapping("/assignment/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        AssignmentDTO assignment = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    // 과제 업데이트
    @PutMapping("/assignment/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long id,
                                              @RequestPart("assignment") AssignmentDTO assignmentDTO,
                                              @RequestPart("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 선택되지 않았습니다.");
        }
        String fileUrl = fileStorageService.uploadFile(file, "assignments"); // 과제 폴더 지정

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        TeacherDTO currentTeacher = teacherService.findByLoginid(currentUserName);

        assignmentDTO.setAuthor(currentTeacher.getName());
        assignmentDTO.setAttachmentPath(fileUrl);

        assignmentService.updateAssignment(id, assignmentDTO);

        return ResponseEntity.ok("과제가 성공적으로 업데이트되었습니다.");
    }

    // 과제 삭제
    @DeleteMapping("/assignment/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok("과제가 성공적으로 삭제되었습니다.");
    }

    // 공지사항 목록 조회
    @GetMapping("/notices")
    public ResponseEntity<List<SubjectNoticeDTO>> getAllNotices() {
        List<SubjectNoticeDTO> notices = subjectNoticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    // 공지사항 조회
    @GetMapping("/notice/{id}")
    public ResponseEntity<SubjectNoticeDTO> getNoticeById(@PathVariable Long id) {
        SubjectNoticeDTO notice = subjectNoticeService.getNoticeById(id);
        return ResponseEntity.ok(notice);
    }

    // 공지사항 생성
    @PostMapping("/notice/new")
    public ResponseEntity<?> createNewNotice(@RequestPart("notice") SubjectNoticeDTO subjectNoticeDTO,
                                             @RequestPart("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileUrl = fileStorageService.uploadFile(file, "notice");
            subjectNoticeDTO.setAttachmentPath(fileUrl);
        }
        subjectNoticeDTO.setCreatedAt(LocalDateTime.now());
        subjectNoticeService.saveNotice(subjectNoticeDTO);
        return ResponseEntity.ok("새로운 공지사항이 생성되었습니다.");
    }

    // 공지사항 수정
    @PutMapping("/notice/{id}")
    public ResponseEntity<?> updateNotice(@PathVariable Long id,
                                          @RequestPart("notice") SubjectNoticeDTO subjectNoticeDTO,
                                          @RequestPart("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileUrl = fileStorageService.uploadFile(file, "notice");
            subjectNoticeDTO.setAttachmentPath(fileUrl);
        }
        subjectNoticeService.updateNotice(id, subjectNoticeDTO);
        return ResponseEntity.ok("공지사항이 성공적으로 업데이트되었습니다.");
    }

    // 공지사항 삭제
    @DeleteMapping("/notice/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id) {
        subjectNoticeService.deleteNotice(id);
        return ResponseEntity.ok("공지사항이 성공적으로 삭제되었습니다.");
    }

}
