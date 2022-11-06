package ru.vsu.cs.eliseev.tasks;

import ru.vsu.cs.eliseev.tasks.affine.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Monolog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel testPanel;
    private JButton addButton;
    private JButton solutionButton;
    private JTextField translationX;
    private JTextField dilatationX;
    private JTextField rotateAngle;
    private JComboBox<Integer> reflectionX;
    private JComboBox<Integer> reflectionY;
    private JTextField translationY;
    private JTextField dilatationY;
    private List<IAffine> transformations = new ArrayList<>();

    public Monolog() {

        setContentPane(contentPane);
        setModal(true);
        reflectionX.addItem(1);
        reflectionX.addItem(-1);
        reflectionY.addItem(1);
        reflectionY.addItem(-1);


        addButton.addActionListener(e -> {

            if (!Objects.equals(translationX.getText(), "")){
                double dx = Double.parseDouble(translationX.getText());
                double dy = Double.parseDouble(translationY.getText());
                transformations.add(new Translation(dx, dy));
            }

            if (!Objects.equals(dilatationX.getText(), "")){
                double kx = Double.parseDouble(dilatationX.getText());
                double ky = Double.parseDouble(dilatationY.getText());
                transformations.add(new Dilatation(kx, ky));
            }

            if (!Objects.equals(rotateAngle.getText(), "")){
                double angle = Double.parseDouble(rotateAngle.getText());
                transformations.add(new Rotation(angle));
            }

            if (Objects.equals(reflectionX.getSelectedItem(), -1) ||  Objects.equals(reflectionY.getSelectedItem(), -1)) {
                transformations.add(new Reflection((int) reflectionX.getSelectedItem(), (int) reflectionY.getSelectedItem()));
            }
        });

        solutionButton.addActionListener(e -> {//полетит в DrawPanel
            System.out.println(transformations.size());
            Transformation tr = new Transformation(transformations);
            transformations.clear();
        });
    }

    public static void main(String[] args) {
        Monolog dialog = new Monolog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
