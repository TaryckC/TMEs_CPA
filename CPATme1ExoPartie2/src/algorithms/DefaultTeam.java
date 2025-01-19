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
    Line line = calculDiametre(copy);
    Point P = line.getP();
    Point Q = line.getQ();
    
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
  
  public int calculeHeron(int a, int b, int c) {
	  int s= (a+b+c)/2;
	  
	  return (int)Math.sqrt(s*(s-a)*(s-b)*(s-c));
  }
  static boolean ontMemeSigne(int x, int y) {
      return (x ^ y) >= 0; // XOR des bits de signe
  }
  
  public Boolean produitVectoriel(Point A, Point B, Point C, Point X) {
	  Vecteur AB= new Vecteur();
	  AB.x= B.x-A.x;
	  AB.y=B.y-A.y;
	  
	  Vecteur BA= new Vecteur();
	  BA.x= A.x-B.x;
	  BA.y=A.y-B.y;
	  
	  Vecteur BC= new Vecteur();
	  BC.x= B.x-C.x;
	  BC.y=B.y-C.y;
	  
	  Vecteur AC= new Vecteur();
	  AC.x= C.x-A.x;
	  AC.y=C.y-A.y;
	  
	  Vecteur AX= new Vecteur();
	  AX.x= X.x-A.x;
	  AX.y=X.y-A.y;
	  
	  Vecteur BX= new Vecteur();
	  BX.x= X.x-B.x;
	  BX.y=X.y-B.y;
	  
	  Vecteur AB_AX= new Vecteur();
	  AB_AX.z= AB.x * AX.y - AB.y * AX.x;
	  
	  Vecteur AB_AC= new Vecteur();
	  AB_AC.z= AB.x * AC.y - AB.y * AC.x;
	  
	  Vecteur BC_BX= new Vecteur();
	  BC_BX.z= BC.x * BX.y - BC.y * BX.x;
	  
	  Vecteur BC_BA= new Vecteur();
	  BC_BA.z= BC.x * BA.y - BC.y * BA.x;
	  
	  Vecteur AC_AX= new Vecteur();
	  AC_AX.z= AC.x * AX.y - AC.y * AX.x;
	  
	  Vecteur AC_AB= new Vecteur();
	  AC_AB.z= AC.x * AB.y - AC.y * AB.x;
	  
	  if (  ontMemeSigne(AB_AX.z,AB_AC.z) && ontMemeSigne(BC_BX.z,BC_BA.z) && ontMemeSigne(AC_AX.z,AC_AB.z)   ) {
		  return true;
	  }
	  
	  return false;
  }
  
  
  
  public boolean barycentrique(Point A, Point B, Point C, Point X) {
	  int d =( (B.y - C.y) * (A.x-C.x) + (C.x - B.x) *(A.y-C.y)    );
	  int l1 = (  (B.y-C.y) * (X.x-C.x) +(C.x-B.x)*(X.y-C.y)     ) / d;
	  int l2=  (  (C.y-A.y) * (X.x-C.x) +(A.x-C.x)*(X.y-C.y)     ) / d;
	  int l3= 1-l1-l2;
	  return  (l1 >= 0 && l1 <= 1) && (l2 >= 0 && l2 <= 1) && (l3 >= 0 && l3 <= 1);
  }
  
  
  public boolean Akl_toussaint_heron(ArrayList<Point> pList,Point X) {
	  
	  Point A=pList.get(0);
	  Point B= pList.get(1);
	  Point C=pList.get(2);
	  Point D=pList.get(3);
	  
	  int AB=(int) distancePoint(A,B);
	  int BC=(int) distancePoint(B,C);
	  int AC=(int) distancePoint(A,C);
	  int BX=(int) distancePoint(B,X);
	  int CX=(int) distancePoint(C,X);
	  int AX= (int) distancePoint(A,X);
	  //Triangle ABX
	  int airABX= calculeHeron(AB,BX,AX);
	  //Triangle BCX
	  int airBCX= calculeHeron(BC,CX,BX);
	  //Triangle ACX
	  int airACX= calculeHeron(AC,CX,AX);
	  //Triangle ABC
	  int airABC= calculeHeron(AB,BC,AC);
	  
	  if ((airABX+airBCX+airACX)==airABC) {
		  return true;
	  }
	  
	  
	  
	  
	  return false;
  }
  class Vecteur{
	  int x;
	  int y;
	  int z;
	  
  }
  
  
}
