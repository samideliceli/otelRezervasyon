package otelrezervasyon;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class OtelRezervasyon {

   

    public OtelRezervasyon() {
      JFrame program=new programArayuz();
      program.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OtelRezervasyon();
            }

        });

    }

}
