package dto;
/**
 * CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 */

public class UsuarioDTO {

    private Integer idUserDto;//IdUserDto
    private String usuarioDto;//UsuarioDto
    private String loginDto;
    private String senhaDto;//SenhaDto
    private String perfilDto;//
    private String cpfDto;
    private String celularDto;
    private String sexoDto;
    private String emailDto;

    public Integer getIdUserDto() {
        return idUserDto;
    }
    public void setIdUserDto(Integer idUserDto) {
        this.idUserDto = idUserDto;
    }

    public String getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(String usuarioDto) {
        this.usuarioDto = usuarioDto;
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

    /**
     * @return the cpfDto
     */
    public String getCpfDto() {
        return cpfDto;
    }

    /**
     * @param cpfDto the cpfDto to set
     */
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

    /**
     * @return the sexoDto
     */
    public String getSexoDto() {
        return sexoDto;
    }

    /**
     * @param sexoDto the sexoDto to set
     */
    public void setSexoDto(String sexoDto) {
        this.sexoDto = sexoDto;
    }

    /**
     * @return the emailDto
     */
    public String getEmailDto() {
        return emailDto;
    }

    /**
     * @param emailDto the emailDto to set
     */
    public void setEmailDto(String emailDto) {
        this.emailDto = emailDto;
    }

    /**
     * @return the loginDto
     */
    public String getLoginDto() {
        return loginDto;
    }

    /**
     * @param loginDto the loginDto to set
     */
    public void setLoginDto(String loginDto) {
        this.loginDto = loginDto;
    }

}
