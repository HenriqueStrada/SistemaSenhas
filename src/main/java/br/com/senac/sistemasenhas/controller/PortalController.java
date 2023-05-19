package br.com.senac.sistemasenhas.controller;
import br.com.senac.sistemasenhas.model.Portal;
import br.com.senac.sistemasenhas.repository.Cadastro;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PortalController implements Initializable {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private TableView<Portal> tblCadastros;
    @FXML
    private TableColumn<Portal, String> clNome;
    @FXML
    private TableColumn<Portal, String> clLogin;
    @FXML
    private TableColumn<Portal, String> clSenha;
    @FXML
    private Button btnApagar;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnLimpar;
    private Cadastro cadastros;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cadastros = Cadastro.getNewInstance();
        configuraColunas();
        configuraTela();
        atualizaDados();
    }

    private void configuraTela() {
        BooleanBinding camposPreenchidos = txtNome.textProperty().isEmpty().or(txtLogin.textProperty()
                .isEmpty()).or(txtSenha.textProperty().isNull());

        BooleanBinding contaSelecionada = tblCadastros.getSelectionModel().
                selectedItemProperty().isNull();
        btnApagar.disableProperty().bind(contaSelecionada);
        btnAtualizar.disableProperty().bind(contaSelecionada);
        btnLimpar.disableProperty().bind(contaSelecionada);

        tblCadastros.getSelectionModel().selectedItemProperty().addListener(
                (b, o, n) -> {
                    if (n != null) {
                        txtNome.setText(n.getNome());
                        txtLogin.setText(n.getLogin());
                        txtSenha.setText(n.getSenha());
                    }
                }
        );
    }

    private void configuraColunas() {
        clNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        clLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        clSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
    }

    public void salvar(){
        Portal cadastro = new Portal();
        pegaValores(cadastro);
        cadastros.salvarCadastro(cadastro);
        atualizaDados();
    }

    private void atualizaDados() {
        tblCadastros.getItems().setAll(cadastros.buscarTodosOsCadastros());
        limpar();
    }

    public void atualizar(){
        Portal p = tblCadastros.getSelectionModel().getSelectedItem();
        pegaValores(p);
        cadastros.atualizarCadastro(p);
        atualizaDados();
    }

    public void limpar() {
        tblCadastros.getSelectionModel().select(null);
        txtNome.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
    }

    public void apagar() {
        Portal p = tblCadastros.getSelectionModel().getSelectedItem();
        cadastros.apagarCadastro(p.getId());
        atualizaDados();
    }
    private void pegaValores(Portal cadastro) {
        cadastro.setNome(txtNome.getText());
        cadastro.setLogin(txtLogin.getText());
        cadastro.setSenha(txtSenha.getText());
    }
}
