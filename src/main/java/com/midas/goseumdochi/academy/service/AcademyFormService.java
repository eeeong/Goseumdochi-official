package com.midas.goseumdochi.academy.service;

import com.midas.goseumdochi.academy.dto.AcademyFormDTO;
import com.midas.goseumdochi.academy.entity.AcademyFormEntity;
import com.midas.goseumdochi.academy.repository.AcademyFormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AcademyFormService {
    private final AcademyFormRepository academyFormRepository;

    // 학원 신청서 처음 등록
    public void submit(AcademyFormDTO academyFormDTO) {
        // 처음 신청서 등록할 때, id(pk)랑 authStatus(default"0")는 값이 없음
        AcademyFormEntity academyFormEntity = AcademyFormEntity.builder()
                .directorName(academyFormDTO.getDirectorName())
                .directorPhoneNumber(academyFormDTO.getDirectorPhoneNumber())
                .directorEmail(academyFormDTO.getDirectorEmail())
                .academyName(academyFormDTO.getAcademyName())
                .academyPhoneNumber(academyFormDTO.getAcademyPhoneNumber())
                .academyPostcode(academyFormDTO.getAcademyPostcode())
                .academyAddress(academyFormDTO.getAcademyAddress())
                .academyAddressDetail(academyFormDTO.getAcademyAddressDetail())
                .build();
        academyFormRepository.save(academyFormEntity);
    }

    // 학원 신청서 수정 저장
    public void update(AcademyFormDTO academyFormDTO) {
        AcademyFormEntity academyFormEntity = AcademyFormEntity.toAcademyFormEntity(academyFormDTO);
        academyFormRepository.save(academyFormEntity);
    }

    // 학원 신청서 수정 가능한지 확인
    public int checkUpdate(AcademyFormDTO academyFormDTO) {
        Optional<AcademyFormEntity> origin = academyFormRepository.findById(academyFormDTO.getId());
        if(origin.isEmpty()) // id 존재하지 않음
            return 0;

        // 원장정보 변경됨 -> 이미 존재한다면 변경할 수 없음
        if(!origin.get().getDirectorName().equals(academyFormDTO.getDirectorName()) ||
                !origin.get().getAcademyPhoneNumber().equals(academyFormDTO.getDirectorPhoneNumber()))
            if(academyFormRepository.findByDirectorNameAndDirectorPhoneNumber(academyFormDTO.getDirectorName(), academyFormDTO.getDirectorPhoneNumber()).isPresent())
                return 0;

        // 원장정보(이름,전화번호) 안 바뀌었거나 or 중복하지 않아서 변경 가능
        return 1;
    }

    // 학원 신청서 찾기 (원장이름, 원장비밀번호) 이용
    public AcademyFormDTO findAcademyForm(String directorName, String directorPhoneNumber) {
        Optional<AcademyFormEntity> resultAcademyForm = academyFormRepository.findByDirectorNameAndDirectorPhoneNumber(directorName, directorPhoneNumber);

        if(resultAcademyForm.isPresent()) { // 찾기 성공
            return AcademyFormDTO.toAcademyFormDTO(resultAcademyForm.get());
        }
        else { // 찾는 결과 없음
            return null;
        }
    }
}
