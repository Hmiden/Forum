package com.Forum.Forum.controller;

import com.Forum.Forum.Services.BadwordService;
import com.Forum.Forum.entity.Badword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/badword")
@RequiredArgsConstructor
public class BadwordController {

    private final BadwordService badwordService;

    @PostMapping
    public Badword addBadword(@RequestBody Map<String, String> payload) {
        String word = payload.get("word");
        if (word == null || word.trim().isEmpty()) {
            throw new RuntimeException("Word cannot be empty");
        }
        return badwordService.addBadword(word.trim());
    }

    @GetMapping
    public List<Badword> getAllBadwords() {
        return badwordService.getAllBadwords();
    }

    @DeleteMapping("/{id}")
    public void deleteBadword(@PathVariable Long id) {
        badwordService.deleteBadword(id);
    }
}
