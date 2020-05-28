import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.util.Scanner;

public class TypePlayerReader {

    public static void main(String[] args){

        String fileName = "fcl/player_type.fcl";
        FIS fis = FIS.load(fileName,true);
        Scanner read = new Scanner(System.in);
        if (fis == null){
            System.err.println("Can't load the file: " + fileName + ".");
            return;
        }
        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        JFuzzyChart.get().chart(functionBlock);

        while(true) {
            System.out.println("VPIP: ");
            float vpip = read.nextFloat();
            fis.setVariable("vpip", vpip);
            System.out.println("PFR: ");
            float pfr = read.nextFloat();
            fis.setVariable("pfr", pfr);

            fis.evaluate();

            Variable var = functionBlock.getVariable("type");
            JFuzzyChart.get().chart(var, var.getDefuzzifier(), true);
            System.out.println(fis);
            System.out.println("Type: " + fis.getVariable("type").getValue());
            System.out.println("Type: " + fis.getVariable("type"));
        }

    }

}
