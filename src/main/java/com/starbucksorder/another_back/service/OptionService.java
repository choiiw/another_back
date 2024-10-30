package com.starbucksorder.another_back.service;

import com.starbucksorder.another_back.dto.admin.request.option.ReqAdminOptionDto;
import com.starbucksorder.another_back.dto.admin.request.option.ReqAdminOptionsDto;
import com.starbucksorder.another_back.dto.admin.response.option.RespAdminOptionDto;
import com.starbucksorder.another_back.dto.user.response.menu.RespOnlyMenuIdAdnName;
import com.starbucksorder.another_back.entity.Menu;
import com.starbucksorder.another_back.entity.Option;
import com.starbucksorder.another_back.repository.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionService {
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private DuplicateService duplicateService;

    // 옵션 추가
    @Transactional(rollbackFor = SQLException.class)
    public boolean add(ReqAdminOptionDto dto) {
        // 중복검사
        duplicateService.isDuplicateName("option", dto.getOptionName());
        Option option = dto.toEntity();

        try {
            System.out.println(option);
            optionMapper.save(option);
            optionMapper.detailSave(option.getOptionId(), dto.getValues());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("데이터베이스 요청 오류" + e.getMessage());
        }
        return true;
    }

    // 옵션 전체 불러오기
    public List<RespAdminOptionDto> getAll() {
        return optionMapper.getAll().stream().map(Option::toOptionAllDto).collect(Collectors.toList());
    }

    // 옵션에 해당하는 메뉴들 불러오기
//    public List<RespOnlyMenuIdAdnName> getAllByOptionIds(ReqAdminOptionsDto dto) {
    public List<RespOnlyMenuIdAdnName> getAllByOptionIds(List<Long> ids) {
//        return optionMapper.getAllByOptionId(dto.getOptionIds()).stream().map(Menu::toRespOnlyIdAndNameDto).collect(Collectors.toList());
        return optionMapper.getAllByOptionId(ids).stream().map(Menu::toRespOnlyIdAndNameDto).collect(Collectors.toList());
    }

    // 옵션 삭제만
    public boolean delete(Long optionId) {
        return optionMapper.deleteByOptionId(optionId) > 0;
    }

    // 옵션 상태수정
    public boolean updateStatus(Long optionId) {
        return optionMapper.updateStatus(optionId) > 0;
    }
}
