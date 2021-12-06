package ru.qnocks.reviewapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.qnocks.reviewapp.dto.TagDto;
import ru.qnocks.reviewapp.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> list() {
        return new ResponseEntity<>(tagService.getAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagDto> show(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tagService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagDto> create(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(tagService.create(tagDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagDto> update(@PathVariable("id") Long id, @RequestBody TagDto tagDto) {
        return new ResponseEntity<>(tagService.update(id, tagDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
