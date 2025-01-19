package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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

  public Point findCenter(Point p1, Point p2) {
      int centreX = (p1.x + p2.x) / 2;
      int centreY = (p1.y + p2.y) / 2;
      return new Point(centreX,centreY);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }
    

    Point center=points.get(0);
    int radius=100;

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    ArrayList<Point> copy = new ArrayList<>(points);
    // Exercice 05
    Random rand = new Random();
    int min = 0, max = points.size()-1;
    int randomInt = rand.nextInt(max - min+1) + min;
    Point dummy= points.get(randomInt);
    int maxDistanceDummy=0;
    Point P= null;
    for(Point p : points) {
    	int newD= (int) distancePoint(dummy,p);
    	if (maxDistanceDummy < newD) {
    		maxDistanceDummy=newD;
    		P = p;
    	}
    	
    }
    int maxDistanceP=0;
    Point Q=null;
    for(Point q: points) {
    	int newD= (int) distancePoint(P,q);
    	if (maxDistanceP < newD) {
    		maxDistanceP=newD;
    		Q = q;
    	}
    }
    
    Point C= findCenter(P,Q);
    Circle CERCLE= new Circle(C,(int) distancePoint(C,P));
    points.remove(P);
    points.remove(Q);
    Iterator<Point> iter = copy.iterator();
   
    while (iter.hasNext()) {
        Point S = iter.next();
        if ((int) distancePoint(C,S) <= CERCLE.getRadius()) {
            iter.remove(); 
        }else {
        	
        	int d = (int) distancePoint(C,S);
        	int Tx = C.x - (CERCLE.getRadius()) * (S.x - C.x) / d;
        	int Ty = C.y - (CERCLE.getRadius()) * (S.y - C.y) / d;
        
            Point T= new Point(Tx,Ty);
            Point newC=  findCenter(T,S);
            C=newC;
            
            int l= (int) distancePoint(C,T);
            CERCLE= new Circle(C,l);
         
        }
    }

 
    return CERCLE;
  }
}
