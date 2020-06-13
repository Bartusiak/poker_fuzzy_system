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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
        String[] hands = {"AA","AKs","AQs","AJs","ATs","A9s","A8s","A7s","A6s","A5s","A4s","A3s","A2s",

                "AKo","KK","KQs","KJs","KTs","K9s","K8s","K7s","K6s","K5s","K4s","K3s","K2s",

                "AQo","KQo","QQ","QJs","QTs","Q9s","Q8s","Q7s","Q6s","Q5s","Q4s","Q3s","Q2s",

                "AJo","KJo","QJo","JJ","JTs","J9s","J8s","J7s","J6s","J5s","J4s","J3s","J2s",

                "ATo","KTo","QTo","JTo","TT","T9s","T8s","T7s","T6s","T5s","T4s","T3s","T2s",

                "A9o","K9o","Q9o","J9o","T9o","99","98s","97s","96s","95s","94s","93s","92s",

                "A8o","K8o","Q8o","J8o","T8o","98o","88","87s","86s","85s","84s","83s","82s",

                "A7o","K7o","Q7o","J7o","T7o","97o","87o","77","76s","75s","74s","73s","72s",

                "A6o","K6o","Q6o","J6o","T6o","96o","86o","76o","66","65s","64s","63s","62s",

                "A5o","K5o","Q5o","J5o","T5o","95o","85o","75o","65o","55","54s","53s","52s",

                "A4o","K4o","Q4o","J4o","T4o","94o","84o","74o","64o","54o","44","43s","42s",

                "A3o","K3o","Q3o","J3o","T3o","93o","83o","73o","63o","53o","43o","33","32s",

                "A2o","K2o","Q2o","J2o","T2o","92o","82o","72o","62o","52o","42o","32o","22"};


        Map<String, Float> mapHands = new HashMap<>();
        for(float i=0;i<hands.length;i++){
            mapHands.put(hands[(int)i], i+1);
        }

        if (radioButton.isSelected()){
            System.out.println("UTG" + tempCards + "\n");
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
            System.out.println(mapHands.get(tempCards));
            fis.setVariable("hand_rank", mapHands.get(tempCards));
            fis.setVariable("stage", 0);
            fis.setVariable("position", 0);
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
