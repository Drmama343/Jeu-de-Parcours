package metier;

import java.awt.Color;

public class Arc
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	private Noeud  depart;
	private Noeud  arriver;
	private String nom;
	private Color  couleur;

	private int valeur;
	
	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/** Constructeur arc
	 * @param nom     nom de l'arc
	 * @param depart  noeud par lequel l'arc part
	 * @param arriver noeud par lequel l'arc repart
	 * @param valeur  points de l'arc
	 */

	private Arc ( String nom, Noeud depart, Noeud arriver, int valeur )
	{
		this.nom        = nom;
		this.depart     = depart;
		this.arriver    = arriver;
		this.valeur     = valeur;
		this.couleur    = null;
	}


	/** Constructeur arc
	 * 
	 * @param nom     nom de l'arc
	 * @param depart  le noeud de départ de l'arc
	 * @param arriver le noeud d'arriver de l'arc
	 * @return retourne l'arc que l'on a créé
	 */

	private Arc ( String nom, Noeud depart, Noeud arriver )
	{
		this ( nom, depart,  arriver, 1 );
	}


	/**Méthode qui crée un arc
	 * @param nom nom de l'arc
	 * @param depart  noeud de départ de l'arc
	 * @param arriver noeud d'arriver de l'arc
	 * @param valeur score de l'arc
	 * @return l'arc que l'on a créé
	 */
	public static Arc creerArc ( String nom, Noeud depart, Noeud arriver, int valeur )
	{
		if ( depart == null || arriver == null || valeur < 0 ) return null;

		return new Arc(nom, depart, arriver, valeur);
	}
	

	/** Méthode qui crée un arc

	 * @param nom nom de l'arc
	 * @param depart noeud de depart de l'arc
	 * @param arriver noeud d'arriver de l'arc
	 * @return l'arc que l'on a créé
	 */

	public static Arc creerArc ( String nom, Noeud depart, Noeud arriver ) 
	{
		if ( depart == null || arriver == null ) return null;
		
		return new Arc ( nom, depart, arriver );
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */
	
	/** Getteur qui permet de récupérer la valeur de l'arc
	 * @return score */
	public int     getValeur     ( ) { return this.valeur;          }

	/** Getteur qui permet de récupérer le noeud de départ de l'arc
	 * @return noeud de départ */
	public Noeud   getNoeudD     ( ) { return this.depart;          }

	/** Getteur qui permet de récupérer le noeud d'arrivée de l'arc
	 * @return noeud d'arrivée */
	public Noeud   getNoeudA     ( ) { return this.arriver;         }

	/** Getteur qui permet de savoir si l'arc est colorié
	 * @return true si l'arc est colorie */
	public boolean getEstColorie ( ) { return this.couleur != null; }

	/** Getteur qui permet de récupérer la couleur de l'arc
	 *  @return couleur de l'arc */
	public Color   getColorArc   ( ) { return this.couleur;         }

	/** Getteur qui permet de récupérer le nom de l'arc
	 * @return nom de l'arc */
	public String  getNom        ( ) { return this.nom;             }

	/* -------------------------------------- */
	/*                Setteur                 */
	/* -------------------------------------- */

	/** Setteur qui permet de changer la valeur de l'arc
	 * @param valeur  valeur de l'arc */
	public void   setValeur  ( int valeur ) { this.valeur  =  valeur; }

	/** Setteur qui permet de changer le noeud de départ de l'arc
	 * @param n noeud de départ de l'arc */
	public void   setNoeudD  ( Noeud n    ) { this.depart  = n;       }

	/** Setteur qui permet de changer le noeud d'arriver de l'arc
	 * @param n noeud d'arriver de l'arc */
	public void   setNoeudA  ( Noeud n    ) { this.arriver = n;       }

	/** Setteur qui permet de colorier l'arc
	 * @param coul couleur de l'arc */
	public void   setColorie ( Color coul ) { this.couleur = coul;    }

	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */


	/**Méthode qui permet de savoir si un arc est identique à un autre
	 * @param arc prend l'arc a teste
	 * @return true si l'arc passé en paramètre est identique
	*/
	public boolean estIdentique ( Arc arc )
	{
		return this.getNoeudD() == arc.getNoeudD() ||
		       this.getNoeudD() == arc.getNoeudA() ||
		       this.getNoeudA() == arc.getNoeudD() ||
		       this.getNoeudA() == arc.getNoeudA();
	}

	/** Méthode toString
	 * @return le nom du noeud de départ et d'arrivée et sa valeur
	 */
	public String toString()
	{
		return "Noeud départ " + this.depart.getNom() + " Noeud arrivé " + this.arriver.getNom() + " valeur " + this.valeur;
	}

}