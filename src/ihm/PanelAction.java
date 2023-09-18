package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import controleur.*;
import metier.Arc;

public class PanelAction extends JPanel implements ActionListener,KeyListener
{
	/*-------------*/
	/*--Attributs--*/
	/*-------------*/
	
	/**
	 * Le Controleur pour pouvoir accéder au Controleur
	 */
	private Controleur   ctrl;

	/**
	 * La frame pour pouvoir accéder à la frame
	 */
	private FrameGraphe  frame;

	/**
	 *Le bouton Colorier
	 */
	private JButton      btnColorier;

	/*----------------*/
	/*--Constructeur--*/
	/*----------------*/

	/** Constructeur de PanelAction ou il ajoute le bouton colorier
	 * @param ctrl  controleur
	 * @param frame frame graphe
	 * 
	 */
	public PanelAction ( Controleur ctrl, FrameGraphe frame )
	{
		this.ctrl = ctrl;
		this.frame = frame;
		
		/*Création des composants*/
		this.btnColorier  = new JButton ( "Colorier" );
	
		/*Placement des composants*/
		this.add ( btnColorier );

		/*Activation des composants*/
		this.btnColorier.addActionListener ( this );
		this.addKeyListener(this);
	}


	/* ActionListener */
	public void actionPerformed ( ActionEvent e )
	{
		this.tester();
	}

	/* KeyListener */ 
	public void keyTyped ( KeyEvent e )
	{
		if ( e.getKeyCode ( ) == KeyEvent.VK_SPACE )
			this.tester ( );
	}

	/**
	 * Test pour voir si un arc est selectionnée et si on peut colorier 
	 */
	public void tester ( )
	{
		Color couleurJoueur   = this.ctrl .getCouleurJouer ( );
		Arc   arcAColorier    = this.frame.getArcAColorier ( );

		if ( this.frame.estSelectionne ( ) && this.ctrl.colorier ( arcAColorier,couleurJoueur ) )
		{
			if( !this.ctrl.augmenterTours ( ) ) //Si on a fini toutes les manches
			{
				String sRet = "";

				sRet += "Nb regions visitées C1 : " + this.ctrl.getNbRegionsVisiteC1 ( ) + "\n";
				sRet += "Nb regions visitées C2 : " + this.ctrl.getNbRegionsVisiteC2 ( ) + "\n";
				sRet += "Nb arcs colorées       : " + this.ctrl.getNbArcsColorie   ( ) + "\n";
				sRet += "Nb Points Total        : " + this.ctrl.calculPoints       ( );

				//Création d'une "Pop-up" pour demander si le joueur veux rejouer ou quitter
				Object[] choix= { "Rejouer","Quitter" };
				int rep = JOptionPane.showOptionDialog ( this.frame,sRet, "Game End", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0] );

				if ( rep == 0 ) //Si Rejouer est sélectionné
				{
					this.frame.dispose ( );	//On ferme le fenêtre
					new Controleur ( );		//On relance une partie
				}
				else
					System.exit ( 1 );			//On ferme le scripte
			}
		}
		else
		{
			JOptionPane.showMessageDialog ( this.frame,"Erreur de sélection", "Erreur", JOptionPane.ERROR_MESSAGE ); //Affiche que la sélection est mauvaise
		}
		this.frame.resetSelect ( );
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
}

