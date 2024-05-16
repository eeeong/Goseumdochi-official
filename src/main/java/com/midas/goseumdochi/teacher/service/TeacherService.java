package com.midas.goseumdochi.teacher.service;

import com.midas.goseumdochi.academy.repository.AcademyRepository;
import com.midas.goseumdochi.director.entity.DirectorEntity;
import com.midas.goseumdochi.director.repository.DirectorRepository;
import com.midas.goseumdochi.teacher.dto.TeacherDTO;
import com.midas.goseumdochi.teacher.entity.TeacherEntity;
import com.midas.goseumdochi.teacher.repository.TeacherRepository;
import com.midas.goseumdochi.util.Dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    final private TeacherRepository teacherRepository;
    final private DirectorRepository directorRepository;
    final private AcademyRepository academyRepository;

    // 선생의 loginid와 password를 설정해야함
    public TeacherDTO setLoginidAndPassword(TeacherDTO teacherDTO) {
        // 선생 로그인 id (원장 로그인id 첫 4자리 + 학원 선생님 수+1 4자리)
        String first4 = directorRepository.findByAcademyId(teacherDTO.getAcademyId()).get().getLoginid().substring(0,4);
        // 학원 선생님 수 구하기 (남는 앞자리 0으로 채우기)
        String last4 = String.format("%04d", teacherRepository.findAllByAcademyId(teacherDTO.getAcademyId()).size() + 1); //! NullException
        /* 위에꺼 에러나면 사용 (에러 안 나면 지워도 됨)
        List<TeacherEntity> teacherEntityList = teacherRepository.findAllByDirectorId(teacherDTO.getDirectorId());
        String last4 = (teacherEntityList == null)? "0001" : String.format("%04d", teacherEntityList.size() + 1);
         */

        teacherDTO.setLoginid(first4 + last4);
        teacherDTO.setPassword("0000");

        return teacherDTO;
    }

    // 선생 처음 등록
    public void regist(TeacherDTO teacherDTO) {
        // 에러나면 처음 static 함수 추가
        TeacherEntity teacherEntity = TeacherEntity.toTeacherEntity(teacherDTO,
                academyRepository.findById(teacherDTO.getAcademyId()).get());
        teacherRepository.save(teacherEntity);
    }

    public MailDTO getMailDTOForRegist(TeacherDTO teacherDTO) {
        DirectorEntity directorEntity = directorRepository.findByAcademyId(teacherDTO.getAcademyId()).get();

        MailDTO mailDTO = new MailDTO(directorEntity.getEmail()
                , String.format("[고슴도치] %s 학원 신규 선생님 임시로그인 정보 전송,", directorEntity.getAcademyEntity().getName())
                , String.format("선생님 이름: %s\n 아이디: %s\n임시비밀번호: %s", teacherDTO.getName(), teacherDTO.getLoginid(), teacherDTO.getPassword()));
        return mailDTO;
    }
}
