package byx.script.server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RunRequestDto {
    @NotEmpty(message = "script cannot be empty")
    private String script;

    private String input = "";
}
