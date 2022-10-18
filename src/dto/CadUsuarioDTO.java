package dto;
/**
 * CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 */

import metodostatics.MetodoStaticosUtil;

public class CadUsuarioDTO {
    private Integer idUserDto;
    private String cpfDto;
    private String loginDto;
    private String senhaDto;
    private String perfilDto;
    private String celularDto;

    public Integer getIdUserDto() {
        return idUserDto;
    }

    public void setIdUserDto(Integer idUserDto) {
        this.idUserDto = idUserDto;
    }

    public String getSenhaDto() {
        return senhaDto;
    }

    public void setSenhaDto(String senhaDto) {
        this.senhaDto = senhaDto;
    }

    public String getPerfilDto() {
        return perfilDto;
    }

    public void setPerfilDto(String perfilDto) {
        this.perfilDto = perfilDto;
    }

    public String getLoginDto() {
        return loginDto;
    }

    public void setLoginDto(String loginDto) {
        this.loginDto = loginDto;
    }

    public String getCpfDto() {
        return cpfDto;
    }

    public void setCpfDto(String cpfDto) {
        this.cpfDto = cpfDto;
    }

    /**
     * @return the celularDto
     */
    public String getCelularDto() {
        return celularDto;
    }

    /**
     * @param celularDto the celularDto to set
     */
    public void setCelularDto(String celularDto) {
        this.celularDto = celularDto;
    }

}
