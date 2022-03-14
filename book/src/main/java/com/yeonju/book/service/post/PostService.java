package com.yeonju.book.service.post;

import com.yeonju.book.web.domain.post.Post;
import com.yeonju.book.web.domain.post.PostRepository;
import com.yeonju.book.web.dto.PostListResponseDto;
import com.yeonju.book.web.dto.PostResponseDto;
import com.yeonju.book.web.dto.PostSaveRequestDto;
import com.yeonju.book.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostResponseDto(entity);
    }

    @Transactional
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

}
