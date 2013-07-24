package br.ifce.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class FiguraFactory {

	public static ImageIcon CIRCULO() {

		BufferedImage buffer = new BufferedImage(50, 50,
				BufferedImage.TRANSLUCENT);
		Graphics2D g = buffer.createGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 50, 50);

		return new ImageIcon(buffer);

	}

	public static ImageIcon QUADRADO() {

		BufferedImage buffer = new BufferedImage(50, 50,
				BufferedImage.TRANSLUCENT);
		Graphics2D g = buffer.createGraphics();
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 50, 50);

		return new ImageIcon(buffer);
	}

	public static ImageIcon TRIANGULO() {
		BufferedImage buffer = new BufferedImage(50, 50,
				BufferedImage.TRANSLUCENT);
		Graphics2D g = buffer.createGraphics();
		Polygon p = new Polygon();
		g.setColor(Color.red);
		p.addPoint(0, 50);
		p.addPoint(25, 0);
		p.addPoint(50, 50);

		g.fillPolygon(p);
		return new ImageIcon(buffer);
	}

}
