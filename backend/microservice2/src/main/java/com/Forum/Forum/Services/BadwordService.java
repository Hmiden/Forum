package com.Forum.Forum.Services;

import com.Forum.Forum.Repository.BadwordRepository;
import com.Forum.Forum.entity.Badword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class BadwordService {

    private final BadwordRepository badwordRepository;

    public Badword addBadword(String word) {
        if (badwordRepository.findByWordIgnoreCase(word).isPresent()) {
            throw new RuntimeException("Badword already exists");
        }
        Badword badword = new Badword();
        badword.setWord(word);
        return badwordRepository.save(badword);
    }

    public List<Badword> getAllBadwords() {
        return badwordRepository.findAll();
    }

    public void deleteBadword(Long id) {
        badwordRepository.deleteById(id);
    }

    /**
     * Checks if a given text contains any bad words and returns a censored version.
     * Replaces bad words with ***
     */
    public String censorText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        List<Badword> badwords = getAllBadwords();
        String censoredText = text;

        for (Badword bw : badwords) {
            // Case insensitive word matching using word boundaries
            String patternString = "(?i)\\b" + Pattern.quote(bw.getWord()) + "\\b";
            censoredText = censoredText.replaceAll(patternString, "***");
        }

        return censoredText;
    }
}
