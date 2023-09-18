package controleur;
/** SAE 2.02
  * date : le 06/06/2023
  * @author : Alizéa Lebaron, Mathys Poret, Maximilien Lesterlin ,Mohamad Marrouche, Eleonore Bouloché
  */

import metier.*;
import ihm.*;
import java.util.List;
import java.awt.Color;

public class Controleur
{
	private GrapheT     metier;
	private	FrameGraphe ihm;
	private Manche      manche;

	/** Contructeur qui initialise le jeu
	 */
	public Controleur ( )
	{
		this.manche = new Manche      (      );
		this.metier = new GrapheT     (      );
		this.ihm    = new FrameGraphe ( this );

		this.manche.nouvellePartie();
	}

	public static void main ( String[] arg )
	{
		new Controleur ( );
	}

	/** Getteur qui retourne la liste des arcsde la partie métier
	 * @return la liste des arcs de la partie métier
	 */
	public List<Arc>   getArcs    ( ) { return this.metier.getArcs    ( ); }

	/** Getteur qui retourne la liste des noeuds de la partie métier
	 * @return la liste des noeuds de la partie métier
	 */
	public List<Noeud> getNoeuds  ( ) { return this.metier.getNoeuds  ( ); }

	/** Getteur qui retourne la couleur du joueur en cours
	 * @return couleur du joueur en cours
	 */
	public Color       getCouleur ( ) { return this.manche.getCouleur ( ); }

	/** Getteur qui retourne le nombre de tour
	 * @return nombre de tour
	 */
	public int         getNbTours ( ) { return Manche.getNbTour       ( ); }

	/** Getteur qui retourne la partie métier
	 * @return partie métier
	 */
	public GrapheT     getMetier  ( ) { return this.metier;                }

	/** Getteur qui retourne le nombre de régions visitées
	 * @return nombre de régions visitées
	 */
	public int   getNbRegionsVisiteC1 ( ) { return this.metier.getRegionvisiteC1 ( ).size ( ); }

	public int   getNbRegionsVisiteC2 ( ) { return this.metier.getRegionvisiteC2 ( ).size ( ); }	
	/** Getteur qui retourne le nombre d'arcs coloriés
	 * @return nombre d'arcs coloriés
	 */
	public int   getNbArcsColorie   ( ) { return this.metier.getNbArcsColorie ( );         }

	/** Getteur qui indique la couleur du joueur actuel
	 * @return color (couleur du joueur actuel)
	 */
	public Color getCouleurJouer ( ) { return this.manche.getCouleur ( ); }

	/** Méthode qui retour l'arc entre deux noeuds
	 * @param n1 noeud 1
	 * @param n2 noeud 2
	 * @return l'arc entre les deux noeuds
	 */
	public Arc     arcEntre ( Noeud n1, Noeud n2 )     { return this.metier.arcEntre ( n1,n2 ); }

	/** Méthode qui permet de colorier un arc en faisant le lien entre l'IHM et le métier
	 * @param arc est l'arc sélectionner dans la partie graphique et l'envoie à la partie métier pour le colorier ou non
	 * @param couleur est la couleur que l'on veut attribuer à l'arc (couleur du joueur )
	 * @return retourne un boolean qui permet de savoir si l'arc a été colorié ou non
	 */
	public boolean colorier ( Arc arc, Color couleur ) { return this.metier.colorier ( arc , couleur ); }

	/** Méthode qui nous indique si on peut changer de tour ou non et le fait si on peut
	 * @return le score du joueur
	 */
	public boolean augmenterTours ( )
	{
		this.manche.augmenterTour( );

		try
		{
			if ( this.manche.getNumTour ( ) >= Manche.getNbTour ( ) )
				this.manche.changerTour ( );

		}catch ( Exception e )
		{
			this.ihm.resetSelect( );
			return false;
		}

		return true;
	}
	
	/** Méthode qui permet de calculer les points du joueur
	 * @return le score du joueur
	 */
	public int calculPoints ( )
	{
		int pointsC1 = 0;
		int pointsC2 = 0;

		Color c1 = Manche.getTabCouleur()[0];
		Color c2 = Manche.getTabCouleur()[1];

		for ( Arc arc : this.metier.getArcs ( ) )
			if ( arc.getEstColorie ( ) && arc.getColorArc() == c1)
				pointsC1 += arc.getValeur ( );

		for ( Arc arc : this.metier.getArcs ( ) )
			if ( arc.getEstColorie ( ) && arc.getColorArc() == c2)
				pointsC2 += arc.getValeur ( );

		
		
		pointsC1 += this.getNbNoeudMaxVisiteParRegion (c1) * this.metier.getRegionvisiteC1 ().size ( );
		pointsC1 += this.getNbNoeudMaxVisiteParRegion (c2) * this.metier.getRegionvisiteC2 ().size ( );

		System.out.println (this.getNbNoeudMaxVisiteParRegion (c1) + " " + this.getNbNoeudMaxVisiteParRegion (c2));


		return pointsC1 + pointsC2;
	}

	/** Méthode qui permet de calculer le nombre de noeuds maximum visité par région
	 * @return int (le nombre de noeuds maximum visité par région)
	 */
	public int getNbNoeudMaxVisiteParRegion(Color c) 
	{
		int max = 0;

		if (c == Manche.getTabCouleur()[0])
		{
			for (int rgn : this.metier.getRegionvisiteC1())
			{
				int nbMax = 0;
				for (Noeud n : this.metier.getNoeuds())
				{
					if (n.getEstVisite() && n.getRegion() == rgn)
					{
						for (Arc a : n.getLstArc())
						{
							if (a.getColorArc() == c)
							{
								nbMax++;
								break;
							}
						}
					}
				}

				if (max < nbMax) {max = nbMax;}
			}
			
		}
		else
		{
			for (int rgn : this.metier.getRegionvisiteC2())
			{
				int nbMax = 0;
				for (Noeud n : this.metier.getNoeuds())
				{
					if (n.getEstVisite() && n.getRegion() == rgn)
					{
						for (Arc a : n.getLstArc())
						{
							if (a.getColorArc() == c)
							{
								nbMax++;
								break;
							}
						}
					}
				}

				if (max < nbMax) {max = nbMax;}
			}
		}
		
		return max;
	}
}
