package io.sommers.packmode.core;

import io.sommers.packmode.PMConfig;
import net.minecraftforge.common.config.Configuration;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ConfigGui extends JDialog {

    public static final String WINDOW_TITLE = "PackMode Selector";
    public String packModeApplied;

    public void setMainThread(Thread mainThread) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainThread.interrupt();
            }
        });
    }

    public ConfigGui(File mcLocation) {
        setTitle(WINDOW_TITLE);
        setBounds(100, 100, 450, 172);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JComboBox<String> comboBox = new JComboBox<>(PMConfig.getAcceptedModes());
        comboBox.setBounds(130, 22, 100, 21);
        contentPanel.add(comboBox);

        JButton button = new JButton("Apply");
        button.addActionListener((e) -> {
            packModeApplied = (String) comboBox.getSelectedItem();
            this.setVisible(false);
            this.dispose();
        });
        contentPanel.add(button);
    }

}
