package ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.List;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

import controleur.*;
import metier.*;

public class PanelGraph extends JPanel implements MouseListener 
{

	/** Un tableau de Couleurs pour colorier les regions
	 *
	 */
	private Color[] couleurs = {Color.BLACK,Color.BLUE,Color.GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE,Color.YELLOW,Color.RED,Color.PINK}; 

	/** Un Controleur pour pouvoir accéder au controleur
	 * 
	 */
	private Controleur  ctrl;

	/** Notre liste d'arcs présents dans notre graph 
	 * 
	 */
	private List<Arc>   lstArc;

	/** Notre liste de noeuds présents dans notre graph 
	 * 
	 */
	private List<Noeud> lstNoeud;

	/**Le premier noeud seléctionné
	 * 
	 */
	private Noeud actif1;

	/**Le deuxième noeud seléctionné
	 * 
	 */
	private Noeud actif2;


	/**Un boolean pour dire si il y avait un arc seléctionné ou pas 
	 * 
	 */
	private boolean selectionne;

	/**
	 * L'arc a colorier
	 */
	private Arc arcAColorier;

	/** Constructeur de PanelGraph
	 * @param ctrl de type Controleur
	 * 
	 */
	public PanelGraph ( Controleur ctrl )
	{
		this.ctrl     = ctrl;
		this.lstArc   = this.ctrl.getArcs   ( );
		this.lstNoeud = this.ctrl.getNoeuds ( );

		this.actif1       = this.actif2 = null;
		this.selectionne  = false;
		this.arcAColorier = null;

		this.addMouseListener ( this );
	}

	public void paintComponent ( Graphics g )
	{
		super.paintComponent ( g );
		Graphics2D g2 = ( Graphics2D ) g;

		Color couleurJouer = this.ctrl.getCouleurJouer ( );

		g2.drawString("Appuyer aussi sur \"espace\" pour dessiner ",290,741);

		//Dessiner la couleur du Jouer
		g2.drawRect ( 750, 670, 20, 20 );
		g2.setColor ( couleurJouer     );
		g2.fillRect ( 750, 670, 20, 20 );

		//Dessiner le graph
		this.dessinerArcs           ( g2 );
		this.dessinerNoeuds         ( g2 );
		this.dessinerNoeudsActifs   ( g2 );
		this.dessinerArcSelectionne ( g2 );
	}

	/** Méthode qui dessine les arcs de la liste
	 * @param g2 de type Graphics2D
	 */
	private void dessinerArcs ( Graphics2D g2 )
	{
		for ( Arc arc : lstArc )
		{
			Color arcColor = arc.getColorArc ( ) == null ? Color.BLACK : arc.getColorArc ( );
			g2.setColor  ( arcColor );
			g2.setStroke ( new BasicStroke ( arc.getColorArc ( ) == null ? 2 : 3 ) );	//Dessine les arcs coloriés avec un stroke plus épais

			Noeud depart  = arc.getNoeudD ( );
			Noeud arrivee = arc.getNoeudA ( );
			g2.drawLine ( depart.getX ( ), depart.getY ( ) - 100, arrivee.getX ( ), arrivee.getY ( ) - 100 );
			
			
			if ( arc.getValeur ( ) != 1 )
			{
				int pointStringY = ( depart.getY ( ) - 100 + arrivee.getY ( ) - 100 ) / 2 - 5 ;
				int pointStringX = 0;

				if (depart.getX() == arrivee.getX())
					pointStringX = ( depart.getX ( ) + arrivee.getX ( ) ) / 2 + 5;
				else
					pointStringX = ( depart.getX ( ) + arrivee.getX ( ) ) / 2;
			
				g2.drawString ( "" + arc.getValeur ( ), pointStringX, pointStringY);
			}
				
		}
	}

	/** Méthode qui dessine les noeuds de la liste
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerNoeuds ( Graphics2D g2 )
	{
		for ( Noeud noeud : lstNoeud ) 
		{
			dessinerOval ( g2, noeud.getX ( ), noeud.getY ( ), this.couleurs[noeud.getRegion ( ) - 1], Color.BLACK );
		}
		
	}

	/** Méthode qui dessine les noeuds actifs en rouge
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerNoeudsActifs ( Graphics2D g2 )
	{
		dessinerNoeud ( g2, actif1, Color.RED );
		dessinerNoeud ( g2, actif2, Color.RED );
	}

	/** Méthode qui dessine un noeud
	 * @param g2 de type Graphics2D
	 * @param node noeud à dessiner
	 * @param color couleur du noeud
	 * 
	 */
	private void dessinerNoeud ( Graphics2D g2, Noeud node, Color color )
	{
		if (node != null)
			dessinerOval(g2, node.getX(), node.getY(), Color.WHITE, color);
		
	}

	/** Méthode qui dessine l'arc sélectionné en rouge
	 * @param g2 de type Graphics2D
	 * 
	 */
	private void dessinerArcSelectionne(Graphics2D g2) 
	{
		if (actif1 != null && actif2 != null) 
		{
			Arc a = ctrl.arcEntre(actif1, actif2);

			if (a != null) {
				Noeud depart = a.getNoeudD();
				Noeud arrivee = a.getNoeudA();
				g2.drawLine(depart.getX(), depart.getY() - 100, arrivee.getX(), arrivee.getY() - 100);

				dessinerNoeud(g2, actif1, Color.RED);
				dessinerNoeud(g2, actif2, Color.RED);

				selectionne = true;
				arcAColorier = a;
			} 
			else
			{
				reset(g2); 
			}
			   
			
		}
	}

	/** Méthode qui reset les noeuds sélectionnés (sélection)
	 * @param g2 de type Graphics2D
	 */
	public void reset(Graphics2D g2) 
	{
		dessinerNoeud(g2, actif1, Color.BLACK);
		dessinerNoeud(g2, actif2, Color.BLACK);
		selectionne = false;
		arcAColorier = null;
		actif1 = null;
		actif2 = null;
		repaint();

		//Pour afficher message d'erreur quand on sélectionne deux noeuds non adjacents
		SwingUtilities.invokeLater(() -> 
		{
			JOptionPane.showMessageDialog(this,"Transgression de contrainte, essaie autre chose bg", "Erreur", JOptionPane.ERROR_MESSAGE);
		});
	}

	/** Reset les noeuds sélectionnés
	 * 
	 */
	public void resetSelect() 
	{
		actif1 = null;
		actif2 = null;
		repaint();
	}

	/** Méthode pour dessiner nos noeuds
	 * @param g2 de type Graphics2D
	 * @param x coordonnée x du noeud
	 * @param y coordonnée y du noeud
	 * @param coulFond couleur de fond du noeud
	 * @param coulTour couleur du tour du noeud
	 * 
	 * 
	 */
	public void dessinerOval(Graphics2D g2, int x, int y, Color coulFond, Color coulTour) 
	{
		g2.setColor(coulFond);
		g2.fillOval(x - 4, y - 104, 10, 10);
		g2.setColor(coulTour);
		g2.drawOval(x - 4, y - 104, 10, 10);
	}

	/** Métode qui permet de savoir si un arc est sélectionné
	 * @return return vrai si il y aun arc selectionné
	 * 
	 */
	public boolean estSelectionne() 
	{
		return selectionne;
	}

	/** Méthode qui permet de récupérer l'arc sélectionné
	 * @return Arc sélectionné
	 * 
	 */
	public Arc getArcAColorier() 
	{
		return arcAColorier;
	}

	public void mouseClicked(MouseEvent e) 
	{
		for (Noeud noeud : lstNoeud)
			//Regarde si la distance entre le centre du noeud et le point du curseur est dans le rayon du noeud dessiné
			if (Point2D.distance(e.getX(),e.getY(),noeud.getX() + 5,noeud.getY()-100) <= 10) 
			{
				if (actif1 == null || (actif1 != null && actif2 != null)) 
				{
					actif1 = noeud;
					actif2 = null;
					repaint();
					return;
				}

				if (actif1 != null && actif2 == null) 
				{
					actif2 = noeud;
					repaint();
					return;
				}
			}
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}