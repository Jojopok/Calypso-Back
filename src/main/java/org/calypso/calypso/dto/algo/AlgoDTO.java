package org.calypso.calypso.dto.algo;

import org.calypso.calypso.model.algo.Difficulty;

import java.util.Date;
import java.util.Set;

public class AlgoDTO {

    private Long id;
    private String title;
    private String content;
    private String answer;
    private Boolean isVisible;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private Set<TypeDTO> type;
    private Set<UserAnswerDTO> userAnswer;
    private Set<DifficultyDTO> difficulty;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<TypeDTO> getType() {
        return type;
    }

    public void setType(Set<TypeDTO> type) {
        this.type = type;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Set<UserAnswerDTO> getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Set<UserAnswerDTO> userAnswer) {
        this.userAnswer = userAnswer;
    }

    public Set<DifficultyDTO> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Set<DifficultyDTO> difficulty) {
        this.difficulty = difficulty;
    }
}