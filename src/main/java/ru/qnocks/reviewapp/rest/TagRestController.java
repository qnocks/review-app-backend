package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.domain.Tag;
import ru.qnocks.reviewapp.dto.TagDto;
import ru.qnocks.reviewapp.repository.TagRepository;
import ru.qnocks.reviewapp.service.DtoMapperService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagRepository tagRepository;

    private final DtoMapperService mapperService;

    @GetMapping
    public ResponseEntity<List<TagDto>> list() {
        List<TagDto> tags = tagRepository.findAll().stream()
                .map(mapperService::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TagDto> show(@PathVariable("id") Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        TagDto tagDto = mapperService.toDto(tag);
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TagDto> create(@RequestBody TagDto tagDto) {
        Tag tag = mapperService.toEntity(tagDto);
        Tag savedTag = tagRepository.save(tag);
        return new ResponseEntity<>(mapperService.toDto(savedTag), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TagDto> update(@PathVariable("id") Long id, @RequestBody TagDto tagDto) {
        Tag tag = mapperService.toEntity(tagDto);
        Tag updatedTag = tagRepository.save(tag);
        return new ResponseEntity<>(mapperService.toDto(updatedTag), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        tagRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
