package br.com.senac.sistemasenhas.repository;
import br.com.senac.sistemasenhas.model.Portal;
import br.com.senac.sistemasenhas.service.PortalService;

import java.util.List;

public interface Cadastro {

    public void salvarCadastro(Portal cadastro);

    public void atualizarCadastro(Portal cadastro);

    public void apagarCadastro(int id);

    public List<Portal> buscarTodosOsCadastros();

    public Cadastro buscarUmCadastro(int id);

    /* esse metodo inicializa a classe responsavel por executar todos
    os metodos disponiveis para o nosso usuário utilizar */

    public static PortalService getNewInstance(){
        return new PortalService();}

}