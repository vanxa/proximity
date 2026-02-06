package com.vanxacloud.appstudio.proximity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private VBox dataContainer;
    @FXML
    private TableView<Person> tableView;
    private List<Person> masterData;

    @FXML
    private void initialize() {
        // search panel
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());
        searchButton.setStyle("-fx-background-color: #457ecd; -fx-text-fill: #ffffff;");

        initTable();
    }

    private void initTable() {
        masterData = new LinkedList<>();
        Person p = new Person("mike", true);
        masterData.add(p);


        tableView = new TableView<>(FXCollections.observableList(masterData));
        TableColumn<Person, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn<Person, String> name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person, Boolean> employed = new TableColumn<>("EMPLOYED");
        employed.setCellValueFactory(new PropertyValueFactory<>("isEmployed"));

        tableView.getColumns().addAll(id, name, employed);
        dataContainer.getChildren().add(tableView);

        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });
    }

    private void loadData() {
        String searchText = searchField.getText();
        Task<ObservableList<Person>> task = new Task<>() {
            @Override
            protected ObservableList<Person> call() throws Exception {
                updateMessage("Loading data");
                return FXCollections.observableArrayList(masterData
                        .stream()
                        .filter(value -> value.getName().toLowerCase().contains(searchText))
                        .collect(Collectors.toList()));
            }
        };
        task.setOnSucceeded(event -> {
            tableView.setItems(FXCollections.observableList(task.getValue()));
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
