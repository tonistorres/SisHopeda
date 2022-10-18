package dto;

/**
 * CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 * persistencia dos dados (model layer) Muito frequentemente voce vera esse
 * padrao sendo usado em conjunto com um DAO. *
 * http://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162
 */
public class LoginDTO {

    private Integer iduserDto;
    private String loginDto;
    private String senhaDto;
    private String perfilDto;
   
    public Integer getIduserDto() {
        return iduserDto;
    }

    
    public void setIduserDto(Integer iduserDto) {
        this.iduserDto = iduserDto;
    }

    
    public String getLoginDto() {
        return loginDto;
    }

    
    public void setLoginDto(String loginDto) {
        this.loginDto = loginDto;
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

}
