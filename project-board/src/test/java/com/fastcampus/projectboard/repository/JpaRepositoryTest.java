package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelectiong_thenWorksFine(){
        //Given

        //When
        List<Article> articles = articleRepository.findAll();
        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(1000);
    }
    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInsertiong_thenWorksFine(){
        //Given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article","new contents","#spring");
        //When
        Article savedAricle= articleRepository.save(article);
        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdationg_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatehashtag = "#springboot";
        article.setHashtag(updatehashtag);
        //When
        Article savedAricle= articleRepository.saveAndFlush(article);
        //Then
        assertThat(savedAricle).hasFieldOrPropertyWithValue("hashtag",updatehashtag);
    }
    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeletiong_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount=articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //When
        articleRepository.delete(article);
        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount -deletedCommentsSize);
    }
}