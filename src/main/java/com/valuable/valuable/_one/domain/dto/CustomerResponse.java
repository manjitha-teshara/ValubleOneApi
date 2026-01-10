package com.valuable.valuable._one.domain.dto;

public class CustomerResponse {

    private Long id;
    private String nic;
    private String fullName;

    public CustomerResponse(Long id, String nic, String fullName) {
        this.id = id;
        this.nic = nic;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public String getNic() {
        return nic;
    }

    public String getFullName() {
        return fullName;
    }
}
