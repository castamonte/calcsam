package org.dyndns.tooman;

import android.util.Log;

// зависимость плотности, спиритуозности и температуры
public class Desterm {
	// спиритуозность - начальная, искомая, ближайшая снизу, ближайшая сверху
	public double s0, s;
	private int s1, s2;
	// плотность - начальная, искомая, ближайшая снизу, ближайшая сверху и вспомогательная переменная
	public double p0, p;
	private double p1, p2 , x;
	// начальная и конечная температуры
	public float t0, t;

	public void setSpiritDefault(float ss) {
		s0 = ss;
	}

	public void setTemperatureDefault(float ts) {
		t0 = ts;
	}

	public void setTemperatureEnding(float ts) {
		t = ts;
	}

	public double getSpiritDefault() {
		return s0;
	}

	public float getTemperatureDefault() {
		return t0;
	}

	public float getTemperatureEnding() {
		return t;
	}

	public double getDensity(float ts, float ss) {
		// находим x из начальной температуры
		x = 1 + 0.05 * ts;
		// находим стандартные спиритуозности
		s1 = (int) (ss % 20) * 20;
		s2 = (int) (1 + ss % 20) * 20;
		// вычисляем плотность для них
		switch (s1) {
		case 0:
			p2 = -0.0278 * x*x*x - 1.0833 * x*x + 0.373 * x + 1001;
			p1 = -11.171 * x + 989.93;
			break;
		case 20:
			p2 = -11.171 * x + 989.93;
			p1 = -12.486 * x + 959.87;
			break;
		case 40:
			p2 = -12.486 * x + 959.87;
			p1 = -13.857 * x + 918.67;
			break;
		case 60:
			p2 = -13.857 * x + 918.67;
			p1 = -14.943 * x + 872.47;
			break;
		case 80:
			p2 = -14.943 * x + 872.47;
			p1 = -18 * x + 825;
			break;
		default:
			Log.i("desterm.java", "что-то с температурой");
		}
		// вычисляем плотность
		p = p2 - ((s2-s0)*(p2-p1)/(s2-s1));
		return p;
	}
	
	public double getSpirit(float ts, float ps) {
		// находим x из начальной температуры
		x = 1 + 0.05 * ts;
		// поочерёдно проверяем плотности при стандартных спиритуозностях
		s1 = 100; s2 = 80;
		p1 = -18 * x + 825;
		p2 = -14.943 * x + 872.47;
		if (ps > p2) {
			s1 = 80; s2 = 60;
			p1 = p2;
			p2 = -13.857 * x + 918.67;
			if (ps > p2) {
				s1 = 60; s2 = 40;
				p1 = p2;
				p2 = -12.486 * x + 959.87;
				if (ps > p2) {
					s1 = 40; s2 = 20;
					p1 = p2;
					p2 = -11.171 * x + 989.93;
					if (ps > p2) {
						s1 = 20; s2 = 0;
						p1 = p2;
						p2 = -0.0278 * x*x*x - 1.0833 * x*x + 0.373 * x + 1001;
						if (ps > p2) {
							Log.i("desterm.java", "что-то с плотностью");
						}
					}
				}
			}
		}
		s = s2 - ((p2-ps)*(s2-s1)/(p2-p1));
 		return s;
	}
}
