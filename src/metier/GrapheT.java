package metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.io.FileInputStream;

public class GrapheT
{
	/* -------------------------------------- */
	/*               Attributs                */
	/* -------------------------------------- */
	
	private List<Noeud>   lstNoeuds;
	private List<Arc>     lstArc;
	private List<Integer> tabRegionVisiteC1;
	private List<Integer> tabRegionVisiteC2;
	
	/* -------------------------------------- */
	/*              Constructeur              */
	/* -------------------------------------- */

	/** Constructeur initialisant le graphe. 
	 * 
	 */
	public GrapheT ( )
	{
		this.lstNoeuds         = new ArrayList<> ( );
		this.lstArc            = new ArrayList<> ( );
		this.tabRegionVisiteC1 = new ArrayList<> ( );
		this.tabRegionVisiteC2 = new ArrayList<> ( );

		this.genererGraphe ( );
	}

	/* -------------------------------------- */
	/*                Accesseur               */
	/* -------------------------------------- */

	/**
	 * @return le nombre de région visitée par la couleur C1
	 */
	public List<Integer> getRegionvisiteC1  ( ) { return this.tabRegionVisiteC1; }

	/**
	 * @return le nombre de région visitée par la couleur C2
	 */
	public List<Integer> getRegionvisiteC2  ( ) { return this.tabRegionVisiteC2; }

	/**
	 * @return le nombre d'arc.
	 */
	public List<Arc>     getArcs          ( ) { return this.lstArc;          }
	/**
	 * @return le nombre de noeud.
	 */
	public List<Noeud>   getNoeuds        ( ) { return this.lstNoeuds;       }

	/** Méthode qui retourne le nombre d'arc colorié.
	 * @return int (le nombre d'arcs qui sont coloriés)
	 */
	public int           getNbArcsColorie ( )
	{
		int nbArcsColorie = 0;
		for(Arc arc : this.lstArc)
			if(arc.getEstColorie())
				nbArcsColorie++;
		
		return nbArcsColorie;
	} 
	
	/* -------------------------------------- */
	/*                 Méthode                */
	/* -------------------------------------- */

	/** Ajouter les régions visitées
	 * @param rgn région
	 */
	public void ajouterRegionvisiteC1 ( int rgn ) { this.tabRegionVisiteC1.add ( rgn ); }

	/** Ajouter les régions visitées
	 * @param rgn région
	 */
	public void ajouterRegionvisiteC2 ( int rgn ) { this.tabRegionVisiteC2.add ( rgn ); }

	/** Méthode permettant génerer les noeuds, arrêtes, régions et arc bonus grâce au fichier .data
	 *
	 */
	public void genererGraphe ( )
	{
		try
		{
			// Prend la première ligne de notre ficher contenant les noeuds du graph
			Scanner sc = new Scanner ( new FileInputStream ( "./donnees/plan.txt" ) );

			//Pour sauter la ligne "[NOEUDS]"
			sc.nextLine ( );

			String line = sc.nextLine ( );
			
			do
			{
				String[] lineSplit  = line.split ( "\t" );
				
				//Créer le sommet
				this.lstNoeuds.add ( new Noeud ( lineSplit[0], Integer.parseInt ( lineSplit[1] )*7, 100-Integer.parseInt ( lineSplit[2] ) *-7) );
	
				line = sc.nextLine ( );
			}
			while ( !line.equals ( "[REGIONS]" ) && !line.equals ( "" ) );

			/* Regions */
			// Pour passer directement à la ligne de la première région
				   sc.nextLine ( ); // bonne ligne
			line = sc.nextLine ( );

			do
			{
				String[] lineSplit  = line.split ( "\t" );

				int numRegion = Integer.parseInt ( lineSplit[0] );
			
				for ( int cpt = 1 ; cpt < lineSplit.length ; cpt++ ) 
				{
					Noeud n = this.lstNoeuds.get ( Integer.parseInt ( lineSplit[cpt] )- 1 );

					n.setRegion ( numRegion );
				}
				line = sc.nextLine ( );
			}
			while ( !line.equals ( "[ARETES]" ) && !line.equals ( "" ) );
			
			sc.nextLine ( ); 

			// Parcours toutes les lignes à partir de la deuxième pour faire les arêtes
			do
			{
				line = sc.nextLine ( );
				String[] lineSplit  = line.split ( "\t" );

				for ( int cpt = 0 ; cpt < lineSplit.length-1 ; cpt++ )
				{
					int point1 = Integer.parseInt ( lineSplit[cpt]   ) - 1;
					int point2 = Integer.parseInt ( lineSplit[cpt+1] ) - 1;

					Arc a = ( Arc.creerArc ( lineSplit[cpt] + "-" + lineSplit[cpt+1] , this.lstNoeuds.get ( point1 ), this.lstNoeuds.get ( point2 ) ));
					
					this.lstArc.add ( a );
					this.lstNoeuds.get ( point1 ).ajouterArc ( a );
					this.lstNoeuds.get ( point2 ).ajouterArc ( a );
				}
					
			}
			while ( !line.equals ( "[ARETES BONUS]" ) && !line.equals ( "" ) );

			sc.nextLine ( ); 

			// Ajoute les valeurs bonus aux arêtes
			while ( sc.hasNextLine ( ) ) 
			{
				line = sc.nextLine ( );
				String[] lineSplit  = line.split ( "\t" );

				this.lstArc.get ( Integer.parseInt ( lineSplit[0] ) ).setValeur ( Integer.parseInt ( lineSplit[1] ) ) ;
			}

			sc.close ( );
		}
		catch ( Exception e ) { e.printStackTrace ( ); }

	}
	
	/** Méthode qui permet de colorier un arc

	 * @param arc arc à colorier
	 * @param couleur couleur souhaiter pour l'arc
	 * @return true si on peut colorier sinon false
	 */
	public boolean colorier ( Arc arc, Color couleur )
	{
		boolean enCours = true;
		
		// On ne peut pas prendre une arête déjà colorié
		if ( arc.getEstColorie ( ) ) return false;
		
		// On ne peut pas colorier une arête qui croise une autre arête coloriée
		for ( Arc a : this.lstArc ) 
			if ( intersection ( arc, a ) && ( a.getColorArc ( ) != null ) ) enCours = false;

		// On ne peut pas former un cycle
		if ( cyclique(arc, couleur)) enCours = false;
		
		//On ne peut pas tromper mille fois une personne... Non attends.. On ne peut pas..
		if ( !estRelie ( arc ) && !Manche.getEstPremierTour ( ) ) enCours = false; 

		// On retourne true et on applique la couleur
		if ( enCours )
		{
			if ( Manche.getEstPremierTour ( ) )
				Manche.setEstPremierTour ( );
				
			arc.getNoeudA ( ).setEstVisite ( );
			arc.getNoeudD ( ).setEstVisite ( );
			
			// On ajoute les régions visitées si elles ne sont pas déjà dans la liste
			
			if (couleur == Manche.getTabCouleur()[0])
			{
				if ( !this.tabRegionVisiteC1.contains ( arc.getNoeudA ( ).getRegion ( ) ) )
					this.tabRegionVisiteC1.add (arc.getNoeudA ( ).getRegion ( ) );

				if ( !this.tabRegionVisiteC1.contains ( arc.getNoeudD ( ).getRegion ( ) ) )
					this.tabRegionVisiteC1.add ( arc.getNoeudD ( ).getRegion ( ) );
			}
			
			if (couleur == Manche.getTabCouleur()[1])
			{
				if ( !this.tabRegionVisiteC2.contains ( arc.getNoeudA ( ).getRegion ( ) ) )
					this.tabRegionVisiteC2.add (arc.getNoeudA ( ).getRegion ( ) );

				if ( !this.tabRegionVisiteC2.contains ( arc.getNoeudD ( ).getRegion ( ) ) )
					this.tabRegionVisiteC2.add ( arc.getNoeudD ( ).getRegion ( ) );
			}
			
			

			arc.setColorie ( couleur );
			return true;
		}
		else
			return false;

	}

	/**Méthode qui prend en paramètre deux noeud et détermine s'ils croisent un autre arc.
	 * @param noeudDep le noeud de départ
	 * @param noeudArr le noeud d'arrivée
	 * @return un arc
	 */
	public Arc arcEntre ( Noeud noeudDep, Noeud noeudArr )
	{
		for ( Arc arc : this.lstArc ) {
			if ( noeudDep == arc.getNoeudA ( ) && noeudArr == arc.getNoeudD ( ) ||
				 noeudDep == arc.getNoeudD ( ) && noeudArr == arc.getNoeudA ( ) ) return arc;
		}
		return null;
	}

	/** Méthode qui retourne si deux arcs se croisent ou non
	 * @param arcOg est l'arc sélectionner par l'utilisateur
	 * @param arcATester est l'arc que l'on veut tester avec l'arc de l'utilisateur
	 * @return renvoie un booléen qui indique si les deux arcs se croisent ou non
	 */
	public boolean intersection ( Arc arcOg, Arc arcATester )
	{
		Noeud depart  = arcOg.getNoeudD ( );
		Noeud arrivee = arcOg.getNoeudA ( );

		Noeud depart2  = arcATester.getNoeudD ( );
		Noeud arrivee2 = arcATester.getNoeudA ( );

		Line2D lineOg      = new Line2D.Double ( depart .getX ( ), depart. getY ( ), arrivee. getX ( ), arrivee. getY ( ) );
		Line2D lineATester = new Line2D.Double ( depart2.getX ( ), depart2.getY ( ), arrivee2.getX ( ), arrivee2.getY ( ) );

		// Vérification si les lignes se croisent
		if ( lineOg.intersectsLine ( lineATester ) )
		{ 
			// Les arêtes se croisent
			// Vérification si les arêtes sont adjacentes
			if ( arcOg.estIdentique ( arcATester ) ) return false; // Les arêtes sont adjacentes, elles ne se croisent pas
			
			return true; // Les arêtes se croisent
		}
		return false; // Les arêtes ne se croisent pas
	}

	/** Méthode qui indique si l'arc prit en paramètre est rataché aux autres arcs déja colorer
	 * @param a est l'arc qui est sélectionner par l'utilisateur
	 * @return renvoie un booléen qui indique si l'arc fait partie du réseaux des autres arcs colorer
	 */
	private boolean estRelie ( Arc a )
	{
		for ( Arc arc : a.getNoeudD ( ).getLstArc ( ) ) 
			if ( arc != a && arc.getEstColorie ( ) ) return true;

		for ( Arc arc : a.getNoeudA ( ).getLstArc ( ) ) 
			if ( arc != a && arc.getEstColorie ( ) ) return true;

		return false;
	}

	/**Retourne si l'arc forme un cycle à partir d'une couleur
	 * @param arc arc sélectionné par l'utilisateur
	 * @param couleur couleur que doit prendre l'arc
	 * @return true si l'arc forme un cycle, sinon false
	 */
	public boolean cyclique ( Arc arc, Color couleur )
	{
		Noeud   noeudD, noeudA;

		boolean cycliqueNoeudD = false;
		boolean cycliqueNoeudA = false;


		noeudD = arc.getNoeudD();
		noeudA = arc.getNoeudA();


		for (Arc a : noeudD.getLstArc() )
			if ( a.getColorArc() == couleur ) cycliqueNoeudD = true;

		for ( Arc a : noeudA.getLstArc() )
			if ( a.getColorArc() == couleur ) cycliqueNoeudA = true;


		return cycliqueNoeudD && cycliqueNoeudA;
	}

}
