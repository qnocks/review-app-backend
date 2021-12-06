package ru.qnocks.reviewapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.qnocks.reviewapp.domain.Tag;
import ru.qnocks.reviewapp.dto.TagDto;
import ru.qnocks.reviewapp.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public List<TagDto> getAll() {
        return tagRepository.findAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TagDto getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> {
                    String e = "Cannot find User with id " + id;
                    log.error(e);
                    throw new IllegalArgumentException(e);
                });

        return mapperService.toDto(tag);
    }

    public TagDto create(TagDto tagDto) {
        Tag tag = mapperService.toEntity(tagDto);
        return mapperService.toDto(tagRepository.save(tag));
    }

    public TagDto update(Long id, TagDto tagDto) {
        Tag tag = mapperService.toEntity(tagDto);
        Tag existingTag = tagRepository.getById(id);
        BeanUtils.copyProperties(tag, existingTag, "id");
        return mapperService.toDto(tagRepository.save(existingTag));
    }

    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
