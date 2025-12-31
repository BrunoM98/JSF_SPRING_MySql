package bm.fit_zone;

import bm.fit_zone.gui.ZoneFitForm;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication

public class FitZoneSwing {
    public static void main(String[] args) {
        // Dark mode
        FlatDarculaLaf.setup();
        // Spring object
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(FitZoneSwing.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        // Swing object
        SwingUtilities.invokeLater(() ->{

            ZoneFitForm zoneFitForm;
            zoneFitForm = contextSpring.getBean(ZoneFitForm.class);
            zoneFitForm.setVisible(true);

        });

    }

}
