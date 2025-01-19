package algorithms;

import java.awt.Point;
import java.util.ArrayList;

/***************************************************************
 * TME 1: calcul de diamètre et de cercle couvrant minimum.    *
 *   - Trouver deux points les plus éloignés d'un ensemble de  *
 *     points donné en entrée.                                 *
 *   - Couvrir l'ensemble de poitns donné en entrée par un     *
 *     cercle de rayon minimum.                                *
 *                                                             *
 * class Circle:                                               *
 *   - Circle(Point c, int r) constructs a new circle          *
 *     centered at c with radius r.                            *
 *   - Point getCenter() returns the center point.             *
 *   - int getRadius() returns the circle radius.              *
 *                                                             *
 * class Line:                                                 *
 *   - Line(Point p, Point q) constructs a new line            *
 *     starting at p ending at q.                              *
 *   - Point getP() returns one of the two end points.         *
 *   - Point getQ() returns the other end point.               *
 ***************************************************************/
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une paire de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(0);
    Point q=points.get(1);
    
    int dist = 0;

    for (Point p1 : points) {
    	for (Point p2 : points) {
    		int curDist = (squaredDistancePoint(p1, p2));
    		if (curDist > dist) {
    			dist = curDist;
    			p = p1;
    			q = p2;
    		    System.out.println("Distance  : " + distancePoint(p, q));
    		}
    	}
    }
    return new Line(p,q);
  }
  
  public double distancePoint(Point p, Point q) {
	  return Math.sqrt((p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y)) ;
  }
  
  public int squaredDistancePoint(Point p, Point q) {
	  return (p.x - q.x) * (p.x - q.x) + (p.y - q.y) * (p.y - q.y) ;
  }
  
  public boolean isPointInCircled(Point center, int radius, Point p) {
	  int valX = center.x - p.x;
	  int valY = center.y - p.y;
	  return radius * radius >= valX * valX + valY * valY;
  }
  
  public Circle genererCercleCirconscrit(Point a, Point b, Point c) {
      // Calcul des dénominateurs pour vérifier que les points ne sont pas alignés
      double d = 2 * (a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y));
      if (d == 0) {
          return null;
      }

      int x = (int)(((a.x * a.x + a.y * a.y) * (b.y - c.y) +
                   (b.x * b.x + b.y * b.y) * (c.y - a.y) +
                   (c.x * c.x + c.y * c.y) * (a.y - b.y)) / d);

      int y = (int)(((a.x * a.x + a.y * a.y) * (c.x - b.x) +
                   (b.x * b.x + b.y * b.y) * (a.x - c.x) +
                   (c.x * c.x + c.y * c.y) * (b.x - a.x)) / d);

      Point centre = new Point(x, y);

      int rayon = (int) distancePoint(a, centre);
      
      if (rayon == 0) {
    	  return null;
      }

      return new Circle(centre, rayon);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    
    // Si le premier cercle crée ayant pour diamètre la taille du segment entre les deux points les plus éloignés est crée
    // Et contient tous les autres points, alors c'est le cercle de rayon minimal et on peut arrêter de chercher.
    
    Point center=points.get(0);
    int radius=100;
    
    int compteur = 0;
    System.out.println("Size of points array : " + points.size());

    for (Point p : points) {
		compteur++;
    	for (Point q : points) {
    		center = new Point((p.x + q.x)/2, (p.y + q.y)/2);
    		radius = (int) (distancePoint(p, q)/2);
    		for (Point c : points) {
    			if (! isPointInCircled(center, radius, c)) {
    				break;
    			}
    			else if (c.equals(points.get(points.size() - 1))) {
    				System.out.println("Fin cercle min - simple - nb itération = " + compteur);
    				return new Circle(center,radius);
    			}
    		}
    		compteur = 0;
    	}
    }
    
    // Sinon, on doit chercher un cercle circonscrit parmis vérifiant cette propriété parmis tous les trios possible de points
    compteur = 0;
    for (Point p : points) {
    	compteur++;
    	System.out.println("Compteur : " + compteur);
    	for (Point q : points) {
    		for (Point r : points) {
    			Circle m = genererCercleCirconscrit(p, q, r);
    			if (m == null) {
    				continue;
    			}
    			center = m.getCenter();
    			radius = m.getRadius();
        		for (Point c : points) {
        			if (! isPointInCircled(center, radius, c)) {
        				break;
        			}
        			else if (c.equals(points.get(points.size() - 1))) {
        				System.out.println("Fin cercle min - hard");
        				System.out.println("Radius : " + radius);
        				System.out.println("Coordonnées centre : " + center);
        				return new Circle(center,radius);
        			}
        		}
    		}
    	}
    }
    return new Circle(center,radius);
  }
}
