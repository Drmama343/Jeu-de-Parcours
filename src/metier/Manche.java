package metier;

import java.awt.Color;

public class Manche
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */

	private static boolean estPremierTour = true;

	private static Color[] couleursJouer  = { Color.BLUE,Color.GREEN };
	
	private static int   nbTours;

	private int   numManche;
	private int   numTour;
	private Color couleur;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/** Constructeur initialisant une manche. 
	 * 
	 */
	public Manche ( )
	{
		this.numManche  = 1;
		this.numTour    = 0;
		Manche.nbTours  = ( int ) ( Math.random ( ) * 6 ) + 5 ;
		this.couleur    = couleursJouer[this.numManche-1];

	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	/** Méthode qui retourne si c'est le premier tour.
	 * @return un boolean qui permet de savoir si c'est le premier tour ou non
	 */
	public static boolean getEstPremierTour( ) { return Manche.estPremierTour;  }

	/** Méthode statique qui retourne le nombre de tour.
	 * @return le nombre de tour
	 */

	public static int     getNbTour        ( ) { return Manche.nbTours;         }
	
	/** Méthode qui retourne le numéro du tour actuel
	 * @return le numéro du tour actuel
	 */
	public int   getNumTour   ( ) { return this.numTour;   }

	/** Méthode qui retourne le numéro de la manche.
	 * Retourne le numéro de la manche
	 * @return le numéro de la manche actuelle
	 */
	public int   getNumManche ( ) { return this.numManche; }
	
	/** Méthode qui retourne la couleur du joueur.
	 * 
	 * @return la couleur du joueur (Color)
	 */
	public Color getCouleur   ( ) { return this.couleur;   }

	public static Color[] getTabCouleur ( ) { return Manche.couleursJouer; }
	
	/* -------------------------------------- */
	/*                 Setteur                */
	/* -------------------------------------- */

	/** Méthode qui permet de changer si c'est le premier tour ou non.
	 */
	public static void setEstPremierTour ( ) { Manche.estPremierTour = false; }

	/* --------------------------------------- */
	/*                 Méthodes                */
	/* --------------------------------------- */

	/** Méthode qui permet d'augmenter le nombre de tour
	 */
	public void augmenterTour  ( ) { this.numTour++; }

	/** Métode qui permet de changer de tour
	 */
	public void changerTour    ( )
	{ 
		Manche.nbTours  = ( int ) ( Math.random ( ) * 6 ) + 5;
		this.numTour = 0;

		this.numManche++;
		this.couleur    = couleursJouer[this.numManche-1];
	}

	/** Méthode qui permet de réinitialiser les statics
	 */
	public void nouvellePartie ( )
	{
		Manche.estPremierTour = true;
		Manche.nbTours  = ( int ) ( Math.random ( ) * 6 ) + 5 ;
	}
}