package otelrezervasyon;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class OtelRezervasyon {

    JFrame frame = new JFrame();
    JTabbedPane tabbedPane = new JTabbedPane();
    musteriIslemleri musteri = new musteriIslemleri();
    odaIslemleri oda = new odaIslemleri();
    muhasebe muhasebe = new muhasebe();
    hizmetler hizmet = new hizmetler();

    public OtelRezervasyon() {
        tabbedPane.add("Müşteri", musteri);
        tabbedPane.add("Oda", oda);
        tabbedPane.add("Hizmetler", hizmet);
        tabbedPane.add("Muhasebe", muhasebe);

        frame.getContentPane().add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(640, 480);
        // frame.pack(); 
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OtelRezervasyon();
            }

        });

    }

}
