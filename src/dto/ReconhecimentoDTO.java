
package dto;
/**
 * CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 */
public class ReconhecimentoDTO {
    private int id_reconhecimentoDto;
    private String dt_hora_conectouDto;
    private String serialHdDto;
    private String serialCPUDto;
    private String serial_placa_maeDto;
    private String informacoes_diversasDto;
    private String liberado_bloqueadoDto;
    private String usuario_responsavel_cadastro;
    public int getId_reconhecimentoDto() {
        return id_reconhecimentoDto;
    }

    public String getDt_hora_conectouDto() {
        return dt_hora_conectouDto;
    }

    public void setDt_hora_conectouDto(String dt_hora_conectouDto) {
        this.dt_hora_conectouDto = dt_hora_conectouDto;
    }

    public String getSerialHdDto() {
        return serialHdDto;
    }

    public void setSerialHdDto(String serialHdDto) {
        this.serialHdDto = serialHdDto;
    }

    public String getSerialCPUDto() {
        return serialCPUDto;
    }

    public void setSerialCPUDto(String serialCPUDto) {
        this.serialCPUDto = serialCPUDto;
    }

    public String getSerial_placa_maeDto() {
        return serial_placa_maeDto;
    }

    public void setSerial_placa_maeDto(String serial_placa_maeDto) {
        this.serial_placa_maeDto = serial_placa_maeDto;
    }

    public String getInformacoes_diversasDto() {
        return informacoes_diversasDto;
    }
    public void setInformacoes_diversasDto(String informacoes_diversasDto) {
        this.informacoes_diversasDto = informacoes_diversasDto;
    }
    public String getLiberado_bloqueadoDto() {
        return liberado_bloqueadoDto;
    }

    public void setLiberado_bloqueadoDto(String liberado_bloqueadoDto) {
        this.liberado_bloqueadoDto = liberado_bloqueadoDto;
    }

    public void setId_reconhecimentoDto(int id_reconhecimentoDto) {
        this.id_reconhecimentoDto = id_reconhecimentoDto;
    }

    public String getUsuario_responsavel_cadastro() {
        return usuario_responsavel_cadastro;
    }

    public void setUsuario_responsavel_cadastro(String usuario_responsavel_cadastro) {
        this.usuario_responsavel_cadastro = usuario_responsavel_cadastro;
    }
}
