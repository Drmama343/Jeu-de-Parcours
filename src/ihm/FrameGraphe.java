package ihm;

import javax.swing.JFrame;
import java.awt.*;

import controleur.*;
import metier.*;

public class FrameGraphe extends JFrame
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/

	/**
	 * Un controleur pour la frame
	 */
	private Controleur  ctrl;

	/**
	 * PanelGraph pour la frame
	 */
	private PanelGraph  panelGraph;

	/**
	 * PanelAction pour la frame
	 */
	private PanelAction panelAction;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructuer de FrameGraphe qui crée un panelGraphe et panelAction
	 * @param ctrl le controleur
	 * 
	 */
	public FrameGraphe ( Controleur ctrl )
	{
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( );
		int l = ( tailleEcran.width  - 825 ) / 2;
		int h = ( tailleEcran.height - 825 ) / 2;
		
		this.setSize     ( 825, 825 );
		this.setLocation ( l , h    );
		this.setTitle    ( "Graphe" );

		this.ctrl = ctrl;

		/*Création des composants*/

		this.panelGraph  = new PanelGraph  ( this.ctrl       );
		this.panelAction = new PanelAction ( this.ctrl, this );

		/*Placement des composants*/
		this.add ( this.panelGraph                     );
		this.add ( this.panelAction,BorderLayout.SOUTH );

		this.setDefaultCloseOperation ( EXIT_ON_CLOSE );
		this.setVisible ( true );
	}

	/** Getteur qui retourne l'arc à colorier
	 * @return l'arc à colorier
	 */
	public Arc     getArcAColorier ( ) { return this.panelGraph.getArcAColorier ( ); }
	
	/** Getteur qui retourne si le panel est sélectionné
	 * @return true si le panel est sélectionné
	 */
	public boolean estSelectionne  ( ) { return this.panelGraph.estSelectionne  ( ); }
	
	/** Appel la méthode resetSelect de PanelGraph
	 * 
	 */
	public void    resetSelect     ( ) {        this.panelGraph.resetSelect     ( ); }
}