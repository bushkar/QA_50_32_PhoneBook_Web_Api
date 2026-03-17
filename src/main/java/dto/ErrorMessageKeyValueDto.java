package dto;

import lombok.*;

import java.util.HashMap;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ErrorMessageKeyValueDto {
    private String timestamp;
    private int status;
    private String error;
    private HashMap message;
    private String path;
}
