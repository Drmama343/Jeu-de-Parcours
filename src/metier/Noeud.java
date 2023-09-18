package metier;

import java.util.ArrayList;
import java.util.List;

public class Noeud
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	private Integer 	rgn;
	private String  	nom;
	private int     	x;
	private int     	y;
	private List<Arc> 	lstArc;
	private boolean 	estVisite;

	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/**
	 * Constructeur
	 * 
	 * @param nom nom du noeud
	 * @param x   position x
	 * @param y   position y
	 */

	public Noeud ( String nom, int x, int y )
	{
		this.nom     = nom;
		this.x       = x;
		this.y       = y;
		this.rgn     = null;
		this.lstArc  = new ArrayList<>();
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */


	/** Getteur qui retourne le nom du noeud
	 * @return nom */
	public String    getNom        ( ) { return this.nom;             }

	/** Getteur qui retourne la position sur x du noeud
	 * @return x */
	public int       getX          ( ) { return this.x  ;             }

	/** Getteur qui retourne la position sur y du noeud
	 * @return y */
	public int       getY          ( ) { return this.y  ;             }

	/** Getteur qui retourne la region du noeud
	 *  @return region */
	public int       getRegion     ( ) { return this.rgn ;            }

	/** Getteur qui retourne la liste des arcs du noeud
	 *  @return liste des arcs */
	public List<Arc> getLstArc     ( ) { return this.lstArc ;         }

	/** Getteur qui retourne si le noeud a été visite
	 *  @return true si le noeud est visite */
	public boolean   getEstVisite  ( ) { return this.estVisite;       }

	/* -------------------------------------- */
	/*                 Setteur                */
	/* -------------------------------------- */

	/** Setteur qui permet de modifier la region du noeud
	 * @param rgn region du noeud */
	public void setRegion     ( int rgn ) { this.rgn = rgn;        }

	/** Setteur qui permet d'indiquer que le noeud a été visité
	 * * passe a vrai la visite du noeud */
	public void setEstVisite  (         ) { this.estVisite = true; }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/** Méthode qui permet d'ajouter un arc au noeud
	 * @param a arc
	 */
	public void ajouterArc ( Arc a ) { this.lstArc.add ( a ); }
	
	/** 
	 * Retourne, la position sur x et y du noeud
	*/
	public String toString ( ) { return "Noeud : " + this.nom + "(" + this.x + " : " + this.y + ")"; }
}
