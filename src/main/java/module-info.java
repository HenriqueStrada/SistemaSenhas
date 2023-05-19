module br.com.senac.sistemasenhas{
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.senac.sistemasenhas.view to javafx.fxml;
    exports br.com.senac.sistemasenhas.view;

    opens br.com.senac.sistemasenhas.controller to javafx.fxml;
    exports br.com.senac.sistemasenhas.controller;

    opens br.com.senac.sistemasenhas.model to javafx.fxml;
    exports br.com.senac.sistemasenhas.model;
}