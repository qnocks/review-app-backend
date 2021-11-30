package ru.qnocks.reviewapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.qnocks.reviewapp.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;



}
