package ru.vsu.cs.eliseev.tasks;

import javax.swing.*;

public class Monolog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton solutionButton;

    public Monolog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        Monolog dialog = new Monolog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
