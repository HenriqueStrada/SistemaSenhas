package br.com.senac.sistemasenhas.service;
import br.com.senac.sistemasenhas.model.Portal;
import br.com.senac.sistemasenhas.repository.Cadastro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PortalService implements Cadastro {

    //Divisor do arquivo ";"
    private static final String  SEPARADOR = ";";

    //Irá salvar o arquivo com os cadastros na pasta do projeto
    private static final Path ARQUIVO_FINAL = Paths.get("./portal.csv");

    private List<Portal> cadastros = new ArrayList<>();

    public PortalService() {carregaDados();}

    @Override
    public void salvarCadastro(Portal cadastro) {
        cadastro.setId(ultimoId() + 1);
        cadastros.add(cadastro);
        salvaDados();
    }

    @Override
    public void atualizarCadastro(Portal cadastro) {
        Portal cadastroAntigo = buscaPorId(cadastro.getId());
        cadastroAntigo.setNome(cadastro.getNome());
        cadastroAntigo.setLogin(cadastro.getLogin());
        cadastroAntigo.setSenha(cadastro.getSenha());
        salvaDados();
    }

    @Override
    public void apagarCadastro(int id) {
        Portal cadastro = buscaPorId(id);
        cadastros.remove(cadastro);
        salvaDados();

    }

    @Override
    public List<Portal> buscarTodosOsCadastros() {
        for (Portal cadastro: cadastros) {
            System.out.println(cadastro.getNome());
        }
        return cadastros;
    }

    @Override
    public Cadastro buscarUmCadastro(int id) { return (Cadastro) buscaPorId(id);}

    private void carregaDados() {
        try{
            if (!Files.exists(ARQUIVO_FINAL)){
                Files.createFile(ARQUIVO_FINAL);
            }

            cadastros = Files.lines(ARQUIVO_FINAL).map(this::leLinha).collect(Collectors.toList());
            for(Portal p : cadastros) {
                System.out.println("Nome: " + p.getNome());
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void salvaDados() {
        StringBuffer sb = new StringBuffer();
        for(Portal p : cadastros){
            String linha = criaLinha(p);
            sb.append(linha);
            sb.append(System.getProperty("line.separator"));
        }
        try{
            Files.delete(ARQUIVO_FINAL);
            Files.write(ARQUIVO_FINAL, sb.toString().getBytes());
        } catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private Portal leLinha(String linha) {
        String colunas[] = linha.split(SEPARADOR);
        int id = Integer.parseInt(colunas[0]);

        Portal conta = new Portal();
        conta.setId(id);
        conta.setLogin(colunas[1]);
        conta.setNome(colunas[2]);
        conta.setSenha(colunas[3]);

        return conta;
    }

    private int ultimoId(){
        if(cadastros == null){
            return 0;
        } else {
            return cadastros.stream().mapToInt(Portal::getId)
                    .max().orElse(0);
        }
    }
    private String criaLinha(Portal cadastro) {
        String idStr = String.valueOf(cadastro.getId());

        //FINALIZA CRIAÇÃO DA LINHA
        String linha = String.join(SEPARADOR, idStr, cadastro.getNome(),
                cadastro.getLogin(), cadastro.getSenha());
        return linha;
    }
    private Portal buscaPorId(int id) {
        return cadastros.stream().filter(p -> p.getId() == id).findFirst()
                .orElseThrow(() -> new Error("Conta não encontrada"));
    }
}
