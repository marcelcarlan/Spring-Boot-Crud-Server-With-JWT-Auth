package com.mobileserver.demo.domain;

public class DeleteRequest {
    private Long id;

    public DeleteRequest(){}

    public DeleteRequest(Long id){
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
