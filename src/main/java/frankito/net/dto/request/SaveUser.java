package frankito.net.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.io.Serializable;

public record SaveUser(
        @NotBlank
        @Size(min = 4,max = 255,message = "{generic.size}")
        String name,
        @Pattern(regexp = "[a-zA-Z0-9-_]{8,255}", message = "{saveUser.username.pattern}")
        String username,
        @Size(min = 4,max = 16,message = "{generic.size}")
        @NotBlank(message = "{generic.notBlank}")
        String password,
        @Size(min = 4,max = 16,message = "{generic.size}")
        @NotBlank(message = "{generic.notBlank}")
        @JsonProperty(value = "password_repeated")
        String passwordRepeated
)implements Serializable {
}
