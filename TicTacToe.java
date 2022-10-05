import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    

	private char whoseTurn = 'X';

    private Cell[][] cells = new Cell[4][4];

    private Label lblStatus = new Label("X's turn to play");
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setTitle("TIC TAC TOE GAME");
        GridPane pane = new GridPane();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pane.add(cells[i][j] = new Cell(),j,i);
            }
        }

        Button button = new Button("One more game");
        BorderPane borderPaneForButton = new BorderPane();
        borderPaneForButton.setCenter(lblStatus);
        borderPaneForButton.setRight(button);

       //lblStatus.setPadding(new Insets(5));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(borderPaneForButton);

        button.setOnAction(event -> {
            pane.getChildren().clear();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    pane.add(cells[i][j] = new Cell(),j,i);
                }
            }
            whoseTurn = 'X';
            lblStatus.setText("X's turn to play");
        });

        Scene scene = new Scene(borderPane,450,460);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean isFill(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cells[i][j].getToken() == ' '){
                    return false;
                }

            }
        }
        return true;
    }

    public boolean isWon(char token){
        for (int i = 0; i < 4; i++) {
            if (cells[i][0].getToken() == token
                && cells[i][1].getToken() == token
                && cells[i][2].getToken() == token
                && cells[i][3].getToken() == token){
                return true;
            }
        }
        for (int i = 0; i < 4; i++) {
            if (cells[0][i].getToken() == token
                    && cells[1][i].getToken() == token
                    && cells[2][i].getToken() == token
                    && cells[3][i].getToken() == token){
                return true;
            }
        }
        if (cells[0][0].getToken() == token
                && cells[1][1].getToken() == token
                && cells[2][2].getToken() == token
                && cells[3][3].getToken() == token){
            return true;
        }
        if (cells[3][0].getToken() == token
                && cells[2][1].getToken() == token
                && cells[1][2].getToken() == token
                && cells[0][3].getToken() == token){
            return true;
        }

        return false;
    }

    public class Cell extends Pane{
        private char token = ' ';

        //constructor
        public Cell(){
            setStyle(" -fx-border-color: black");
            this.setPrefSize(2000,2000);
            this.setOnMouseClicked(event -> handleMouseClick());
        }

        //getter and setter
        public char getToken() {
            return token;
        }


        public void setToken(char c) {
            token = c;

            if (token == 'X'){
                Line line1 = new Line(10,10,this.getWidth()-10,this.getHeight()-10);
                line1.endXProperty().bind(this.widthProperty().subtract(10));
                line1.endYProperty().bind(this.heightProperty().subtract(10));
                Line line2 = new Line(10,this.getHeight() - 10, this.getWidth()-10,10);
                line2.startYProperty().bind(this.heightProperty().subtract(10));
                line2.endXProperty().bind(this.widthProperty().subtract(10));

                this.getChildren().addAll(line1,line2);
            }else if (token == 'O'){
                Ellipse ellipse = new Ellipse(this.getWidth()/2,this.getHeight()/2,
                        this.getWidth()/2 - 10,this.getHeight()/2 - 10);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
                ellipse.setFill(Color.GREY);
                ellipse.setStroke(Color.BLACK);

                this.getChildren().add(ellipse);
            }

        }

        private void handleMouseClick() {

            if (token == ' ' && whoseTurn != ' '){
                setToken(whoseTurn);

                if (isWon(whoseTurn)) {
                    lblStatus.setText(whoseTurn+" won! The game is over!");
                    whoseTurn = ' ';
                }else if (isFill()){
                    lblStatus.setText("Draw! The game is over!");
                    whoseTurn = ' ';
                }else {
                    whoseTurn = (whoseTurn == 'X')? 'O': 'X';
                    lblStatus.setText(whoseTurn+"'s turn");
                }
            }
        }

    }
}