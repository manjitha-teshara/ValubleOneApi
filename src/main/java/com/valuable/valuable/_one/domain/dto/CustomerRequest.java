package com.valuable.valuable._one.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequest {
    @NotBlank
    @Size(max = 12)
    private String nic;

    @NotBlank
    private String fullName;

    public @NotBlank @Size(max = 12) String getNic() {
        return nic;
    }

    public CustomerRequest setNic(@NotBlank @Size(max = 12) String nic) {
        this.nic = nic;
        return this;
    }

    public @NotBlank String getFullName() {
        return fullName;
    }

    public CustomerRequest setFullName(@NotBlank String fullName) {
        this.fullName = fullName;
        return this;
    }
}
