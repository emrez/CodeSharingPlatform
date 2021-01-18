package com.platform.codesharing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class CodeSnippetService {

    private final CodeSnippetRepository snippetRepository;

    private void updateSnippet(CodeSnippet snippet) {
    }

    private boolean existsByUuid(String uuid) {
        return snippetRepository.existsByUuid(uuid);
    }

    @Autowired
    public CodeSnippetService(CodeSnippetRepository snippetRepository) {
        this.snippetRepository = snippetRepository;
    }

    public Long count() {
        return snippetRepository.count();
    }

    CodeSnippet findByUuid(String uuid) throws Exception {
        Optional<CodeSnippet> snippetOption = Optional.ofNullable(snippetRepository.findByUuid(uuid));
        CodeSnippet snippet = snippetOption.orElseThrow(() -> new Exception("Not Found -- UUID does not exist"));
        if (snippet.isSecret()) {
            if (snippet.isSecretByTime()) {
                long passedSeconds = snippet.getTimePasses();
                snippet.setTime(passedSeconds > snippet.getTime() ? 0L : snippet.getTime() - passedSeconds);
                if (snippet.getTime() == 0) {
                    snippetRepository.deleteById(snippet.getId());
                    throw new Exception("ERROR: TIME___________Restricted");
                } else {
                    snippetRepository.save(snippet);
                }

            }
            if (snippet.isSecretByView()) {
                snippet.setViews(snippet.getViews() > 0 ? snippet.getViews() - 1 : 0L);
                if (snippet.getViews() == 0) {
                    snippetRepository.deleteById(snippet.getId());
                } else {
                    snippetRepository.save(snippet);
                }
            }
        }
        return snippet;
    }

    ArrayList<CodeSnippet> getLatest() {
        Pageable sortedByLastUpdateDesc = PageRequest.of(0, 10, Sort.by("date").descending());
//        snippetRepository.findAllByTimeIsLessThanEqualAndViewsIsLessThanEqualOrderByDateDesc(
        return new ArrayList<>(snippetRepository
                .findAllBySecretOrderByDateDesc(false, sortedByLastUpdateDesc)
                .getContent());
    }

    public CodeSnippet save(CodeSnippet codeSnippet) {
        boolean secretByTime = codeSnippet.getTime() > 0;
        boolean secretByView = codeSnippet.getViews() > 0;
        codeSnippet.setSecretByTime(secretByTime);
        codeSnippet.setSecretByView(secretByView);
        codeSnippet.setSecret(secretByView || secretByTime);
        return snippetRepository.save(codeSnippet);
    }


}
