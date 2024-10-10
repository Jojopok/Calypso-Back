package org.calypso.calypso.dto.algo;

public class CommentDTO {

    private Long id;
    private String content;
    private Integer rate;
    private Long algoId;
    private Long userId;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getAlgoId() {
        return algoId;
    }

    public void setAlgoId(Long algoId) {
        this.algoId = algoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}