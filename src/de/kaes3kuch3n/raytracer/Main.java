package de.kaes3kuch3n.raytracer;

import de.kaes3kuch3n.raytracer.display.ImagePanel;
import de.kaes3kuch3n.raytracer.display.Window;
import de.kaes3kuch3n.raytracer.objects.CSG;
import de.kaes3kuch3n.raytracer.objects.Light;
import de.kaes3kuch3n.raytracer.objects.Sphere;
import de.kaes3kuch3n.raytracer.utilities.Operator;
import de.kaes3kuch3n.raytracer.utilities.Vector3;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.linear.RealMatrix;

import javax.swing.*;
import java.awt.*;

public class Main {
    private Scene scene;
    private RealMatrix sphere1Mat;
    private RealMatrix sphere2Mat;

    private void show() {
        Camera camera = new Camera(new Vector3(0d, 0d, 4d), new Vector3(0d, 0d, 3d), 0);
        scene = new Scene(camera);

        CSG sphere = new Sphere(1, new Color(255, 0, 0));
        CSG sphere2 = new Sphere(1, new Color(19, 255, 0));

        sphere.translate(-0.6, 0, 0);
        sphere2.translate(0.6, 0, 0);
        sphere1Mat = sphere.q;
        sphere2Mat = sphere2.q;

        sphere.intersect(sphere2);

        scene.addCSGs(sphere);

        scene.addLights(
                new Light(new Vector3(2, 0, 3), new Color(255, 255, 255), 1f)
                //new Light(new Vector3(3, 0, 3), new Color(255, 255, 255), 1f)
                //new Light(new Vector3(0, 0, 15), new Color(255, 255, 255), 1f)
        );

        Window window = new Window(800, 800);

        ImagePanel imagePanel = getRenderedImage(window.getSize());
        window.addResizeListener(size -> imagePanel.updateImage(scene.renderImage(size)));
        window.setImage(imagePanel);

        window.addSliderListener(e -> {
            Object evtSrc = e.getSource();
            if (evtSrc instanceof JSlider) {
                double angle = ((JSlider) evtSrc).getValue();
                sphere.q = sphere1Mat;
                sphere2.q = sphere2Mat;
                sphere.rotateY(angle);
                sphere2.rotateY(angle);
                imagePanel.updateImage(scene.renderImage(window.getSize()));
            }
        });
    }

    private void Update() {
        while (true) {

        }
    }

    private ImagePanel getRenderedImage(Dimension size) {
        return new ImagePanel(scene.renderImage(size));
    }

    public static void main(String[] args) {
        new Main().show();
    }
}
