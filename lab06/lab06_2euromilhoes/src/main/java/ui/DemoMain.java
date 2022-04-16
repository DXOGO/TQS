package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoMain {

    /**
     * demonstrates a client for ramdom euromillions bets
     */
    public static void main(String[] args) {

        // played sheet
        CuponEuromillions thisWeek = new CuponEuromillions();
        Logger.getLogger(DemoMain.class.getName()).log(Level.INFO, "Betting with three random bets...");
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());

        // simulate a random draw
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();

        //report results
        Logger.getLogger(DemoMain.class.getName()).log(Level.INFO, "You played: \n {0}", thisWeek.format());

        Logger.getLogger(DemoMain.class.getName()).log(Level.INFO, "Draw results: \n {0}", draw.getDrawResults().format());

        Logger.getLogger(DemoMain.class.getName()).log(Level.INFO, "Your Score: ");
        for (Dip dip : draw.findMatches(thisWeek)) {
            Logger.getLogger(DemoMain.class.getName()).log(Level.INFO,"{0}", dip.format());
        }
    }
}