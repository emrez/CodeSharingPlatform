package com.platform.codesharing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeSnippetRepository extends PagingAndSortingRepository<CodeSnippet, Long>, JpaSpecificationExecutor<CodeSnippet> {
    CodeSnippet findByUuid(String uuid);
    boolean existsByUuid(String uuid);

    Page<CodeSnippet> findAllByTimeIsLessThanEqualAndViewsIsLessThanEqualOrderByDateDesc(Long time, Long views, Pageable pageable);
    Page<CodeSnippet> findAllBySecretOrderByDateDesc(boolean secret, Pageable pageable);
//    Page<CodeSnippet> findAllOrderByDateDesc(Pageable pageable);
}
