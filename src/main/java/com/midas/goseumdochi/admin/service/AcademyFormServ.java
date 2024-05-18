package com.midas.goseumdochi.admin.service;

import com.midas.goseumdochi.academy.entity.AcademyEntity;
import com.midas.goseumdochi.academy.repository.AcademyRepository;
import com.midas.goseumdochi.director.entity.DirectorEntity;
import com.midas.goseumdochi.academy.entity.AcademyFormEntity;
import com.midas.goseumdochi.academy.repository.AcademyFormRepository;
import com.midas.goseumdochi.director.repository.DirectorRepository;
import com.midas.goseumdochi.util.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AcademyFormServ {

    private final AcademyFormRepository academyFormRepository;
    private final AcademyRepository academyRepository;

    private final DirectorRepository directorRepos;


    // 원장 id,pw 메일 전송
    private final MailService mailService;


    @Autowired
    public AcademyFormServ(AcademyFormRepository academyFormRepository, AcademyRepository academyRepository, DirectorRepository directorRepos, MailService mailService) {
        this.academyFormRepository = academyFormRepository;
        this.academyRepository = academyRepository;
        this.directorRepos = directorRepos;
        this.mailService = mailService;
    }



    public List<AcademyFormEntity> getAllAcademyForms() {
        return academyFormRepository.findAll();
    }

    public void acceptAcademyForm(Long academyFormId) {
        AcademyFormEntity academyFormEntity = academyFormRepository.findById(academyFormId)
                .orElseThrow(() -> new RuntimeException("학원신청서를 찾을 수 없음: " + academyFormId));

        // 수락 처리
        academyFormEntity.setAuthStatus(1); // 수락 상태로 변경 (1로 변경)
        academyFormRepository.save(academyFormEntity);

        // 학원 정보 저장
        AcademyEntity academyEntity = AcademyEntity.builder()
                .name(academyFormEntity.getAcademyName())
                .phoneNumber(academyFormEntity.getAcademyPhoneNumber())
                .postcode(academyFormEntity.getAcademyPostcode())
                .address(academyFormEntity.getAcademyAddress())
                .addressDetail(academyFormEntity.getAcademyAddressDetail())
                .build();
        academyRepository.save(academyEntity);

        String phoneNumber = academyFormEntity.getDirectorPhoneNumber();
        String lastFourDigits = phoneNumber.substring(phoneNumber.length() - 4);

        // 원장 정보 저장 (아이디: 학원신청서 기본키 번호 4자리 + 폰 번호 마지막 네자리)
        String academyFormIdStr = String.format("%04d", academyFormId); // 학원신청서 기본키번호를 네 자리 숫자로 포맷팅
        String directorId = academyFormIdStr + "0000"; // 원장 ID 생성
        DirectorEntity directorEntity = DirectorEntity.builder()
                .name(academyFormEntity.getDirectorName())
                .phoneNumber(academyFormEntity.getDirectorPhoneNumber())
                .password("0000") // 비밀번호 초기화
                .loginid(directorId) // 원장 ID 설정
                .build();
        directorRepos.save(directorEntity);
        mailService.directorMailSend(academyFormEntity.getDirectorEmail(), directorId); // 원장 id를 이메일로 전송
    }

    public void rejectAcademyForm(Long academyFormId) {
        AcademyFormEntity academyFormEntity = academyFormRepository.findById(academyFormId)
                .orElseThrow(() -> new RuntimeException("학원신청서를 찾을 수 없음: " + academyFormId));

        // 거절 처리
        academyFormEntity.setAuthStatus(-1); // 거절 상태로 변경
    }


}
