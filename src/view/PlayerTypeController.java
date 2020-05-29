package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerTypeController implements Initializable{

    @FXML
    private TextField vpipTextField;

    @FXML
    private TextField pfrTextField;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
