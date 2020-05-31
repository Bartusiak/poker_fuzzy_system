package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class PlayerTypeController implements Initializable{

    private boolean bool = false;
    private String tempCards = "";
    private Label prevLabel;
    private Background tempStyle;

    @FXML
    private TextField vpipTextField;
    @FXML
    private TextField pfrTextField;
    @FXML
    private RadioButton radioButton;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private RadioButton radioButton3;
    @FXML
    private RadioButton radioButton4;
    @FXML
    private RadioButton radioButton5;
    @FXML
    private ToggleGroup positions;
    @FXML
    private Label results;
    @FXML
    private Label label00;
    @FXML
    private GridPane gridPaneHands;

    private String gridPosition;

    @FXML
    private void handleButton(javafx.event.ActionEvent actionEvent) {
        if(!vpipTextField.getText().isEmpty() && !pfrTextField.getText().isEmpty()){
            System.out.println("START");
            String fileName = "fcl/player_type.fcl";
            FIS fis = FIS.load(fileName,true);
            if (fis == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Load File");
                alert.setHeaderText("Something wrong with load the file");
                alert.setContentText("Can't load the file: " + fileName + ".");
                return;
            }
            FunctionBlock functionBlock = fis.getFunctionBlock(null);
            JFuzzyChart.get().chart(functionBlock);
            try {
                float vpip = Float.valueOf(vpipTextField.getText());
                float pfr = Float.valueOf(pfrTextField.getText());

                fis.setVariable("vpip", vpip);
                fis.setVariable("pfr", pfr);

                fis.evaluate();

                Variable var = functionBlock.getVariable("type");
                JFuzzyChart.get().chart(var, var.getDefuzzifier(), true);
                //System.out.println(fis);
                //System.out.println("Type: " + fis.getVariable("type").getValue());
                //System.out.println("Type: " + fis.getVariable("type"));
            }
            catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inputs errror");
                alert.setHeaderText("Something wrong with inputs - VPIP and PFR");
                alert.setContentText(e.getMessage());
            }
            System.out.println("STOP");
        }
    }

    @FXML
    private void applyButton(javafx.event.ActionEvent actionEvent){
        if (radioButton.isSelected()){
            System.out.println("UTG" + tempCards);
            results.setText("UTG " + tempCards);
            System.out.println(gridPosition);
            String fileName = "fcl/poker_play.fcl";
            FIS fis = FIS.load(fileName,true);
            if (fis == null){
                System.err.println("Can't load the file: " + fileName + ".");
                return;
            }
            FunctionBlock functionBlock = fis.getFunctionBlock(null);
            JFuzzyChart.get().chart(functionBlock);
            fis.setVariable("hand_rank", parseInt(gridPosition));
            fis.setVariable("stage", 1);
            fis.setVariable("position", 1);
            fis.evaluate();
            Variable var = functionBlock.getVariable("play");
            JFuzzyChart.get().chart(var, var.getDefuzzifier(), true);
            System.out.println(fis);
            System.out.println("Type: " + fis.getVariable("play").getValue());
            System.out.println("Type: " + fis.getVariable("play"));
        }
        if (radioButton1.isSelected()){
            System.out.println("MP " + tempCards);
            results.setText("Position MP " + tempCards);
        }
        if (radioButton2.isSelected()){
            System.out.println("CO" + tempCards);
            results.setText("Position CO " + tempCards);
        }
        if (radioButton3.isSelected()){
            System.out.println("BTN" + tempCards);
            results.setText("Position BTN " + tempCards);
        }
        if (radioButton4.isSelected()){
            System.out.println("SB" + tempCards);
            results.setText("Position SB " + tempCards);
        }
        if (radioButton5.isSelected()){
            System.out.println("BB" + tempCards);
            results.setText("Position BB " + tempCards);
        }
        if (bool=true) {
            bool = false;
            prevLabel.setBackground(tempStyle);
            tempCards = "";
        }
    }

    @FXML
    private void checkLabel(MouseEvent mouseEvent){
        if(bool == false) {
            Label label = (Label) mouseEvent.getSource();
            gridPosition = "";
            gridPosition = String.valueOf(gridPaneHands.getRowIndex(label));
            gridPosition = gridPosition + String.valueOf(gridPaneHands.getColumnIndex(label));
            tempStyle = label.getBackground();
            label.getStyleClass().add("back-selected");
            bool = true;
            tempCards = label.getText();
            prevLabel = (Label) mouseEvent.getSource();
        }
        else{
            prevLabel.setBackground(tempStyle);
            tempCards = "";
            bool = false;
            checkLabel(mouseEvent);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioButton.setToggleGroup(positions);
        radioButton1.setToggleGroup(positions);
        radioButton2.setToggleGroup(positions);
        radioButton3.setToggleGroup(positions);
        radioButton4.setToggleGroup(positions);
        radioButton5.setToggleGroup(positions);
    }

}
