package br.com.fiap.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class EmailDTO {
    private String aluno;
    private String curso;
    private Short nota;
    private String msg;
    private String email;
}
