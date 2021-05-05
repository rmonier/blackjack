/* FICHIER MENUS.JAVA :
 *      - MENU PRINCIPAL
 *      - MENU JOUER
 *      - MENU REGLES_JEU
 *      - MENU OPTIONS
 *      - MENU CRÉDITS
 *      - QUITTER
 *
 *  DERNIÈRE MÀJ : 09/04/2019 par ROMAIN MONIER
 *  CRÉÉ PAR ROMAIN MONIER
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE DE VUE
 *      - Menus
 *      - Gestion des événements
 * ------------------------------------------
 */

package blackjack;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.ImageIcon;

import java.awt.event.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/** Menus
 * @author Romain MONIER, Yohann CHAMBRIER
 */
public class Menus extends JFrame implements MouseListener, ActionListener
{
    private Bouton bouton_jouer, bouton_regles_jeu, bouton_options, bouton_credits, bouton_quitter, bouton_retour, bouton_son, bouton_musique, bouton_reset, bouton_comptage;
    private Bouton bouton_distrib, bouton_assurance, bouton_double, bouton_reste, bouton_split, bouton_surrender, bouton_jeton_1, bouton_jeton_5, bouton_jeton_25;
    private BaseDeDonnees bdd;
    private Audio musique;
    private Deck decks[];
    private JTextArea zone_momo, zone_argent, zone_mise, zone_points_joueur, zone_points_croupier, zone_compteur;
    private ImageIcon img_musique_on, img_musique_on_ok, img_musique_off, img_musique_off_ok, img_son_on, img_son_on_ok, img_son_off, img_son_off_ok, img_comptage_on, img_comptage_on_ok, img_comptage_off, img_comptage_off_ok;
    private String texte_id_final;
    private JLayeredPane cont_cartes, cont_img_fin;
    private Timer timer_carte1, timer_carte2, timer_carte3, timer_carte_croupier, timer_action, timer_fin, timer_restart;
    private boolean freeze;

    /**
     * Constructeur
     * @author Romain MONIER, Yohann CHAMBRIER
     * @param bdd Adresse Base de Données
     * @param musique Audio
     */
    public Menus(BaseDeDonnees bdd, Audio musique) throws IOException
    {
        this.setTitle("BLACKJACK");
        this.setSize(750, 830);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage((new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/icones/icone.png"))).getImage()));

        this.bdd = bdd;

        this.musique = musique;

        if(Parametres.isMusicActive())
            musique.jouer();

        Analyse.init(bdd);
    }

    /**
     * Menu principal
     * @author Romain MONIER
     */
    public void principal() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        ImageIcon img_principal, img_titre;
        JLabel label_img_principal, label_img_titre;
        Point pos_img_principal, pos_img_titre;

        /*  MISE EN PLACE DES CONTAINERS    */

        JLayeredPane cont_full = new JLayeredPane();
        JLayeredPane cont_front = new JLayeredPane();

        /*  CHARGEMENT DES IMAGES AVEC LEUR POSITION   */

        img_principal = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/principal.png")));
        label_img_principal = new JLabel(img_principal);

        pos_img_principal = new Point(0,0);

        cont_full.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_front.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());

        label_img_principal.setBounds(pos_img_principal.x, pos_img_principal.y, img_principal.getIconWidth(), img_principal.getIconHeight());

        img_titre = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/titre.png")));
        label_img_titre = new JLabel(img_titre);

        pos_img_titre = new Point((cont_full.getWidth() / 2) - (img_titre.getIconWidth() / 2),0);
        label_img_titre.setBounds(pos_img_titre.x, pos_img_titre.y, img_titre.getIconWidth(), img_titre.getIconHeight());

        bouton_jouer = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jouer.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jouer_ok.gif"))), new Point(220, 200));

        bouton_regles_jeu = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_regles_jeu.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_regles_jeu_ok.gif"))), new Point(220, 310));

        bouton_options = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_options.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_options_ok.gif"))), new Point(220, 420));

        bouton_credits = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_credits.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_credits_ok.gif"))), new Point(220, 530));

        bouton_quitter = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_quitter.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_quitter_ok.gif"))), new Point(220, 640));

        /*  AJOUT DES LISTENERS */

        bouton_quitter.addMouseListener(this);
        bouton_credits.addMouseListener(this);
        bouton_options.addMouseListener(this);
        bouton_regles_jeu.addMouseListener(this);
        bouton_jouer.addMouseListener(this);

        /*  EMBRIQUER & PLACER LES CONTAINERS    */

        bouton_quitter.setOpaque(false);
        bouton_credits.setOpaque(false);
        bouton_options.setOpaque(false);
        bouton_regles_jeu.setOpaque(false);
        bouton_jouer.setOpaque(false);

        cont_front.add(bouton_quitter);
        cont_front.add(bouton_credits);
        cont_front.add(bouton_options);
        cont_front.add(bouton_regles_jeu);
        cont_front.add(bouton_jouer);
        cont_front.add(label_img_titre);

        cont_full.add(cont_front, 2);
        cont_full.add(label_img_principal, 1);

        cont_full.setLocation(pos_img_principal);

        this.setContentPane(cont_full);
    }

    /**
     * Menu Jouer
     * @author Romain MONIER, Yohann CHAMBRIER, Julie BALOUET
     */
    public void jouer() throws UnsupportedAudioFileException, IOException, LineUnavailableException, FontFormatException
    {
        ImageIcon img_principal, img_momo;
        JLabel label_img_principal, label_img_momo;
        String texte_momo;
        Point pos_img_principal, pos_img_momo, pos_zone_momo, pos_zone_argent, pos_zone_mise, pos_zone_points_joueur, pos_zone_points_croupier, pos_zone_compteur;
        Font font = Font.createFont(Font.TRUETYPE_FONT, Blackjack.getResourceStream("font/FUTURA MEDIUM BT.TTF")).deriveFont(Font.PLAIN, 40);
        Font font_big = font.deriveFont(Font.BOLD, 80);
        Font font_little = font.deriveFont(Font.PLAIN, 15);
        decks = new Deck[]{new Deck(), new Deck(), new Deck(), new Deck(), new Deck(), new Deck()}; // 6 decks de 52 cartes

        /*  NOUVELLE PARTIE  */

        Analyse.reset();
        freeze = false;

        /*  MISE EN PLACE DES CONTAINERS    */

        JLayeredPane cont_full = new JLayeredPane();
        JLayeredPane cont_front = new JLayeredPane();
        cont_cartes = new JLayeredPane();
        cont_img_fin = new JLayeredPane();

        /*  CHARGEMENT DES IMAGES AVEC LEUR POSITION   */

        img_principal = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/jouer.png")));
        label_img_principal = new JLabel(img_principal);

        pos_img_principal = new Point(0,0);

        cont_full.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_front.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_cartes.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_img_fin.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());

        label_img_principal.setBounds(pos_img_principal.x, pos_img_principal.y, img_principal.getIconWidth(), img_principal.getIconHeight());

        img_momo = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo.png")));
        label_img_momo = new JLabel(img_momo);

        pos_img_momo = new Point(0, 0);
        label_img_momo.setBounds(pos_img_momo.x, pos_img_momo.y, img_momo.getIconWidth(), img_momo.getIconHeight());

        bouton_assurance = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_assurance.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_assurance_ok.png"))), new Point(20, 690));
        bouton_double = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_double.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_double_ok.png"))), new Point(170, 690));
        bouton_reste = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_reste.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_reste_ok.png"))), new Point(320, 690));
        bouton_split = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_split.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_split_ok.png"))), new Point(470, 690));
        bouton_surrender = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_surrender.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_surrender_ok.png"))), new Point(620, 690));

        bouton_jeton_1 = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_1.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_1_ok.png"))), new Point(10, 560));
        bouton_jeton_5 = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_5.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_5_ok.png"))), new Point(160, 560));
        bouton_jeton_25 = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_25.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_jeton_25_ok.png"))), new Point(300, 560));

        bouton_distrib = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/dos.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/dos_ok.png"))), new Point(130, 280));

        bouton_retour = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour_fleche.png"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour_fleche_ok.png"))), new Point(10, 300));

        /*  AFFICHAGE TEXTES */

        if(Analyse.getArgent() > 0)
            texte_momo = "Salut, je suis Momo et je serai ta croupière ! Choisis ta mise puis clique sur le paquet de cartes pour commencer !";
        else
            texte_momo = "Salut, je suis Momo et je serai ta croupière ! Tu as 0$ ! Pour avoir 100$ va dans Options et réinitialise ton argent !";
        zone_momo = new JTextArea(texte_momo);
        zone_momo.setFont(font_little);
        zone_momo.setWrapStyleWord(true);
        zone_momo.setLineWrap(true);
        zone_momo.setEditable(false);
        zone_momo.setFocusable(false);
        zone_momo.setForeground(Color.BLACK);
        pos_zone_momo = new Point(235, 22);
        zone_momo.setBounds(pos_zone_momo.x, pos_zone_momo.y, 220, 90);

        zone_argent = new JTextArea("ARGENT : " + Analyse.getArgent() + "$");
        zone_argent.setFont(font);
        zone_argent.setEditable(false);
        zone_argent.setFocusable(false);
        zone_argent.setForeground(Color.WHITE);
        pos_zone_argent = new Point(10, 500);
        zone_argent.setBounds(pos_zone_argent.x, pos_zone_argent.y, 380, 50);

        zone_points_joueur = new JTextArea("POINTS : " + Analyse.getPointsJoueur());
        zone_points_joueur.setFont(font);
        zone_points_joueur.setEditable(false);
        zone_points_joueur.setFocusable(false);
        zone_points_joueur.setForeground(Color.WHITE);
        pos_zone_points_joueur = new Point(10, 450);
        zone_points_joueur.setBounds(pos_zone_points_joueur.x, pos_zone_points_joueur.y, 380, 50);

        zone_points_croupier = new JTextArea("POINTS : " + Analyse.getPointsCroupier());
        zone_points_croupier.setFont(font);
        zone_points_croupier.setEditable(false);
        zone_points_croupier.setFocusable(false);
        zone_points_croupier.setForeground(Color.WHITE);
        pos_zone_points_croupier = new Point(10, 200);
        zone_points_croupier.setBounds(pos_zone_points_croupier.x, pos_zone_points_croupier.y, 380, 50);

        zone_compteur = new JTextArea("RC : " + Analyse.getCompte());
        zone_compteur.setFont(font_little);
        zone_compteur.setEditable(false);
        zone_compteur.setFocusable(false);
        zone_compteur.setForeground(Color.WHITE);
        pos_zone_compteur = new Point(650, 10);
        zone_compteur.setBounds(pos_zone_compteur.x, pos_zone_compteur.y, 100, 25);

        zone_mise = new JTextArea("MISE EN JEU\n" + Analyse.getMise() + "$");
        zone_mise.setFont(font);
        zone_mise.setWrapStyleWord(true);
        zone_mise.setLineWrap(true);
        zone_mise.setEditable(false);
        zone_mise.setFocusable(false);
        zone_mise.setForeground(Color.WHITE);
        pos_zone_mise = new Point(445, 560);
        zone_mise.setBounds(pos_zone_mise.x, pos_zone_mise.y, 300, 100);

        /*  AJOUT DES LISTENERS */

        bouton_retour.addMouseListener(this);
        bouton_jeton_1.addMouseListener(this);
        bouton_jeton_5.addMouseListener(this);
        bouton_jeton_25.addMouseListener(this);
        bouton_surrender.addMouseListener(this);
        bouton_assurance.addMouseListener(this);
        bouton_double.addMouseListener(this);
        bouton_reste.addMouseListener(this);
        bouton_split.addMouseListener(this);
        bouton_distrib.addMouseListener(this);

        /*  EMBRIQUER & PLACER LES CONTAINERS    */

        bouton_retour.setOpaque(false);
        bouton_jeton_1.setOpaque(false);
        bouton_jeton_5.setOpaque(false);
        bouton_jeton_25.setOpaque(false);
        bouton_distrib.setOpaque(false);
        bouton_surrender.setOpaque(false);
        bouton_assurance.setOpaque(false);
        bouton_double.setOpaque(false);
        bouton_reste.setOpaque(false);
        bouton_split.setOpaque(false);
        zone_momo.setOpaque(false);
        zone_argent.setOpaque(false);
        zone_mise.setOpaque(false);
        zone_points_joueur.setOpaque(false);
        zone_compteur.setOpaque(false);
        zone_points_croupier.setOpaque(false);

        cont_front.add(cont_img_fin);
        cont_front.add(zone_momo);
        if(Parametres.isComptageActive())
            cont_front.add(zone_compteur);
        cont_front.add(zone_argent);
        cont_front.add(zone_mise);
        cont_front.add(zone_points_joueur);
        cont_front.add(zone_points_croupier);
        cont_front.add(bouton_retour);
        cont_front.add(bouton_jeton_1);
        cont_front.add(bouton_jeton_5);
        cont_front.add(bouton_jeton_25);
        cont_front.add(bouton_distrib);
        cont_front.add(bouton_surrender);
        cont_front.add(bouton_assurance);
        cont_front.add(bouton_double);
        cont_front.add(bouton_reste);
        cont_front.add(bouton_split);
        cont_front.add(label_img_momo);
        cont_front.add(cont_cartes);

        cont_full.add(cont_front, 2);
        cont_full.add(label_img_principal, 1);

        cont_full.setLocation(pos_img_principal);

        this.setContentPane(cont_full);
    }

    /**
     * Menu Regles Jeu
     * @author Romain MONIER, Morgane BOUCHARD
     */
    public void regles_jeu() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        ImageIcon img_principal;
        JLabel label_img_principal;
        Point pos_img_principal;

        /*  MISE EN PLACE DES CONTAINERS    */

        JLayeredPane cont_full = new JLayeredPane();
        JLayeredPane cont_front = new JLayeredPane();

        /*  CHARGEMENT DES IMAGES AVEC LEUR POSITION   */

        img_principal = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/regles_jeu.png")));
        label_img_principal = new JLabel(img_principal);

        pos_img_principal = new Point(0,0);

        cont_full.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_front.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());

        label_img_principal.setBounds(pos_img_principal.x, pos_img_principal.y, img_principal.getIconWidth(), img_principal.getIconHeight());

        bouton_retour = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour_ok.gif"))), new Point(50, 640));

        /*  AJOUT DES LISTENERS */

        bouton_retour.addMouseListener(this);

        /*  EMBRIQUER & PLACER LES CONTAINERS    */

        bouton_retour.setOpaque(false);

        cont_front.add(bouton_retour);

        cont_full.add(cont_front, 2);
        cont_full.add(label_img_principal, 1);

        cont_full.setLocation(pos_img_principal);

        this.setContentPane(cont_full);
    }

    /**
     * Menu Crédits
     * @author Romain MONIER, Yohann CHAMBRIER, Morgane BOUCHARD
     */
    public void credits() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        ImageIcon img_principal, img_titre;
        JLabel label_img_principal, label_img_titre;
        Point pos_img_principal, pos_img_titre;

        /*  MISE EN PLACE DES CONTAINERS    */

        JLayeredPane cont_full = new JLayeredPane();
        JLayeredPane cont_front = new JLayeredPane();

        /*  CHARGEMENT DES IMAGES AVEC LEUR POSITION   */

        img_principal = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/credits.png")));
        label_img_principal = new JLabel(img_principal);

        pos_img_principal = new Point(0,0);

        cont_full.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_front.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());

        label_img_principal.setBounds(pos_img_principal.x, pos_img_principal.y, img_principal.getIconWidth(), img_principal.getIconHeight());

        img_titre = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/titre_credits.png")));
        label_img_titre = new JLabel(img_titre);

        pos_img_titre = new Point((cont_full.getWidth() / 2) - (img_titre.getIconWidth() / 2),0);
        label_img_titre.setBounds(pos_img_titre.x, pos_img_titre.y, img_titre.getIconWidth(), img_titre.getIconHeight());

        bouton_retour = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour_ok.gif"))), new Point(50, 640));

        /*  AJOUT DES LISTENERS */

        bouton_retour.addMouseListener(this);

        /*  EMBRIQUER & PLACER LES CONTAINERS    */

        bouton_retour.setOpaque(false);

        cont_front.add(bouton_retour);
        cont_front.add(label_img_titre);

        cont_full.add(cont_front, 2);
        cont_full.add(label_img_principal, 1);

        cont_full.setLocation(pos_img_principal);

        this.setContentPane(cont_full);
    }

    /**
     * Menu Options
     * @author Romain MONIER
     */
    public void options() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        ImageIcon img_principal, img_titre;
        JLabel label_img_principal, label_img_titre;
        Point pos_img_principal, pos_img_titre;

        /*  MISE EN PLACE DES CONTAINERS    */

        JLayeredPane cont_full = new JLayeredPane();
        JLayeredPane cont_front = new JLayeredPane();

        /*  CHARGEMENT DES IMAGES AVEC LEUR POSITION   */

        img_principal = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/options.png")));
        label_img_principal = new JLabel(img_principal);

        pos_img_principal = new Point(0,0);

        cont_full.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());
        cont_front.setBounds(pos_img_principal.x, pos_img_principal.y, this.getWidth(), this.getHeight());

        label_img_principal.setBounds(pos_img_principal.x, pos_img_principal.y, img_principal.getIconWidth(), img_principal.getIconHeight());

        img_titre = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/titre_options.png")));
        label_img_titre = new JLabel(img_titre);

        pos_img_titre = new Point((cont_full.getWidth() / 2) - (img_titre.getIconWidth() / 2),0);
        label_img_titre.setBounds(pos_img_titre.x, pos_img_titre.y, img_titre.getIconWidth(), img_titre.getIconHeight());

        img_musique_on = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_musique_on.gif")));
        img_musique_on_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_musique_on_ok.gif")));
        img_musique_off = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_musique_off.gif")));
        img_musique_off_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_musique_off_ok.gif")));
        img_son_on = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_son_on.gif")));
        img_son_on_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_son_on_ok.gif")));
        img_son_off = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_son_off.gif")));
        img_son_off_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_son_off_ok.gif")));
        img_comptage_on = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_comptage_on.gif")));
        img_comptage_on_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_comptage_on_ok.gif")));
        img_comptage_off = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_comptage_off.gif")));
        img_comptage_off_ok = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_comptage_off_ok.gif")));

        if(Parametres.isMusicActive())
            bouton_musique = new Bouton(img_musique_on, img_musique_on_ok, new Point(220, 200));
        else
            bouton_musique = new Bouton(img_musique_off, img_musique_off_ok, new Point(220, 200));

        if(Parametres.isSoundActive())
            bouton_son = new Bouton(img_son_on, img_son_on_ok, new Point(220, 310));
        else
            bouton_son = new Bouton(img_son_off, img_son_off_ok, new Point(220, 310));

        if(Parametres.isComptageActive())
            bouton_comptage = new Bouton(img_comptage_on, img_comptage_on_ok, new Point(220, 420));
        else
            bouton_comptage = new Bouton(img_comptage_off, img_comptage_off_ok, new Point(220, 420));

        bouton_reset = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_reset.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_reset_ok.gif"))), new Point(220, 530));
        bouton_retour = new Bouton(new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour.gif"))), new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/boutons/bouton_retour_ok.gif"))), new Point(220, 640));

        /*  AJOUT DES LISTENERS */

        bouton_musique.addMouseListener(this);
        bouton_son.addMouseListener(this);
        bouton_comptage.addMouseListener(this);
        bouton_reset.addMouseListener(this);
        bouton_retour.addMouseListener(this);

        /*  EMBRIQUER & PLACER LES CONTAINERS    */

        bouton_musique.setOpaque(false);
        bouton_son.setOpaque(false);
        bouton_comptage.setOpaque(false);
        bouton_reset.setOpaque(false);
        bouton_retour.setOpaque(false);

        cont_front.add(bouton_musique);
        cont_front.add(bouton_son);
        cont_front.add(bouton_comptage);
        cont_front.add(bouton_reset);
        cont_front.add(bouton_retour);
        cont_front.add(label_img_titre);

        cont_full.add(cont_front, 2);
        cont_full.add(label_img_principal, 1);

        cont_full.setLocation(pos_img_principal);

        this.setContentPane(cont_full);
    }

    /*  EVENEMENTS  */

    /**
     * Event click
     * @author Romain MONIER, Julie BALOUET
     * @param event l'event
     */
    public void mouseClicked(MouseEvent event)
    {
        try
        {
            if(SwingUtilities.isLeftMouseButton(event))
            {
                /*  MENUS GENERAUX  */

                if(event.getSource() == bouton_quitter){
                    bdd.close();
                    System.exit(0);
                }
                else if(event.getSource() == bouton_credits){
                    this.credits();
                }
                else if(event.getSource() == bouton_options){
                    this.options();
                }
                else if(event.getSource() == bouton_regles_jeu){
                    this.regles_jeu();
                }
                else if(event.getSource() == bouton_jouer){
                    this.jouer();
                }
                else if(event.getSource() == bouton_reset){
                    Analyse.resetArgent();
                }

                else if(event.getSource() == bouton_son)
                {
                    Parametres.setSon(!Parametres.isSoundActive());

                    if(Parametres.isSoundActive())
                        bouton_son.setImage(img_son_on, img_son_on_ok);
                    else
                        bouton_son.setImage(img_son_off, img_son_off_ok);
                }

                else if(event.getSource() == bouton_comptage)
                {
                    Parametres.setComptage(!Parametres.isComptageActive());

                    if(Parametres.isComptageActive())
                        bouton_comptage.setImage(img_comptage_on, img_comptage_on_ok);
                    else
                        bouton_comptage.setImage(img_comptage_off, img_comptage_off_ok);
                }

                else if(event.getSource() == bouton_musique)
                {
                    Parametres.setMusique(!Parametres.isMusicActive(), musique);
                    if(Parametres.isMusicActive())
                        bouton_musique.setImage(img_musique_on, img_musique_on_ok);
                    else
                        bouton_musique.setImage(img_musique_off, img_musique_off_ok);

                }

                else if(event.getSource() == bouton_retour)
                {
                    if(timer_carte1 != null)
                        timer_carte1.stop();
                    if(timer_carte2 != null)
                        timer_carte2.stop();
                    if(timer_carte3 != null)
                        timer_carte3.stop();
                    if(timer_carte_croupier != null)
                        timer_carte_croupier.stop();
                    if(timer_action != null)
                        timer_action.stop();
                    if(timer_fin != null)
                        timer_fin.stop();
                    if(timer_restart != null)
                        timer_restart.stop();
                    this.principal();
                }

                /*  MENU JOUER  */

                else if(event.getSource() == bouton_jeton_1 && !freeze)
                {
                    if(!Analyse.isMiseFaite())
                    {
                        if(Analyse.miseArgent(1))
                        {
                            zone_momo.setText("Tu ajoutes 1$ à ta mise ! La prudence ne fait de mal à personne !");
                            if(Analyse.getArgent() <= 0)
                                zone_momo.append(" Wow, tu as tout misé ! Bonne chance...");
                            zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
                            zone_mise.setText("MISE EN JEU\n" + Analyse.getMise() + "$");
                        }
                        else
                            zone_momo.setText("Tu n'as pas assez d'argent pour miser cette somme !");
                    }
                    else
                        zone_momo.setText("Tu as déjà placé ta mise !");
                }

                else if(event.getSource() == bouton_jeton_5 && !freeze)
                {
                    if(!Analyse.isMiseFaite())
                    {
                        if(Analyse.miseArgent(5))
                        {
                            zone_momo.setText("Tu ajoutes 5$ à ta mise! Tu flaires le bon plan ?");
                            if(Analyse.getArgent() <= 0)
                                zone_momo.append(" Wow, tu as tout misé ! Bonne chance...");
                            zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
                            zone_mise.setText("MISE EN JEU\n" + Analyse.getMise() + "$");
                        }
                        else
                            zone_momo.setText("Tu n'as pas assez d'argent pour miser cette somme !");
                    }
                    else
                        zone_momo.setText("Tu as déjà placé ta mise !");
                }

                else if(event.getSource() == bouton_jeton_25 && !freeze)
                {
                    if(!Analyse.isMiseFaite())
                    {
                        if(Analyse.miseArgent(25))
                        {
                            zone_momo.setText("Tu ajoutes 25$ à ta mise ! Tu as raison, vivons dangereusement !");
                            if(Analyse.getArgent() <= 0)
                                zone_momo.append(" Wow, tu as tout misé ! Bonne chance...");
                            zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
                            zone_mise.setText("MISE EN JEU\n" + Analyse.getMise() + "$");
                        }
                        else
                            zone_momo.setText("Tu n'as pas assez d'argent pour miser cette somme !");
                    }
                    else
                        zone_momo.setText("Tu as déjà placé ta mise !");
                }

                else if(event.getSource() == bouton_distrib && !freeze)
                {
                    if(Analyse.getMise() > 0)
                    {
                        if(!Analyse.isMiseFaite()) // si on commence la partie
                        {
                            Analyse.miseFaite();

                            /*  CARTES JOUEUR   */

                            Carte nouvelle_carte = Analyse.getCarteAleatoire(decks);
                            Analyse.addCarteJoueur(nouvelle_carte);
                            nouvelle_carte.setPosition(new Point(300, 280));
                            nouvelle_carte.setOpaque(false);
                            cont_cartes.add(nouvelle_carte);
                            nouvelle_carte.setVisible(true);

                            zone_points_joueur.setText("POINTS : " + Analyse.getPointsJoueur());
                            zone_compteur.setText("RC : " + Analyse.getCompte());

                            zone_momo.setText("Et de une...");

                            freeze = true;

                            timer_carte1 = new Timer(1000, this);
                            timer_carte1.start();

                        }
                        else // si on demande une nouvelle carte
                        {
                            zone_momo.setText("Et hop, voici une nouvelle carte ! ");

                            Carte nouvelle_carte = Analyse.getCarteAleatoire(decks);
                            Analyse.addCarteJoueur(nouvelle_carte);
                            nouvelle_carte.setPosition(new Point(400+(Analyse.getTour()*50), 280));
                            nouvelle_carte.setOpaque(false);
                            cont_cartes.add(nouvelle_carte);
                            nouvelle_carte.setVisible(true);

                            zone_points_joueur.setText("POINTS : " + Analyse.getPointsJoueur());
                            zone_compteur.setText("RC : " + Analyse.getCompte());

                            if(Analyse.getPointsJoueur() > 21)
                            {
                                freeze = true;
                                zone_momo.append("Aïe ! Tu as brûlé !");
                                timer_action = new Timer(2000, this);
                                timer_action.start();
                            }
                            else if(Analyse.getPointsJoueur() == 21)
                            {
                                freeze = true;
                                zone_momo.append("21 ! Joli !");
                                timer_action = new Timer(2000, this);
                                timer_action.start();
                            }
                            else {
                                zone_momo.append("Que fais-tu maintenant ?");
                                affComptage();
                            }

                            Analyse.newTour();
                        }
                    }
                    else
                        zone_momo.setText("Hop hop hop ! Tu ne peux pas jouer sans miser de l'argent ! Clique sur les jetons !");
                }

                else if(event.getSource() == bouton_assurance && !freeze)
                {
                    if(Analyse.isMiseFaite() && Analyse.getTour() == 0)
                    {
                        if(!Analyse.isAssurance())
                        {
                            if(Analyse.isAssuranceDispo())
                            {
                                if(Analyse.getArgent() >= (int)(Analyse.getMise() / 2))
                                {
                                    zone_momo.setText("Bah alors, on est parano ? Tu prends l'assurance contre un Blackjack, tu payes donc la moitié de ta mise initiale !");

                                    Analyse.takeAssurance();

                                    if(Analyse.getArgent() <= 0)
                                        zone_momo.append(" Wow, tu as tout misé ! Bonne chance...");
                                    zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
                                }
                                else
                                    zone_momo.setText("Tu ne peux pas doubler ta mise, tu n'as plus assez d'argent !");
                            }
                            else
                                zone_momo.setText("Tu ne peux pas prendre l'assurance, je n'ai pas d'As !");
                        }
                        else
                            zone_momo.setText("Tu as déjà pris une assurance !");
                    }
                    else
                        zone_momo.setText("Tu ne peux pas effectuer cette action à ce stade de la partie !");
                }

                else if(event.getSource() == bouton_double && !freeze)
                {
                    if(Analyse.isMiseFaite() && Analyse.getTour() == 0)
                    {
                        if(Analyse.getArgent() >= Analyse.getMise())
                        {
                            zone_momo.setText("Tu doubles ta mise, audacieux ! Voilà une dernière carte pour toi !");

                            Analyse.miseArgent(Analyse.getMise());
                            if(Analyse.getArgent() <= 0)
                                zone_momo.append(" Wow, tu as tout misé ! Bonne chance...");
                            zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
                            zone_mise.setText("MISE EN JEU\n" + Analyse.getMise() + "$");

                            Carte nouvelle_carte = Analyse.getCarteAleatoire(decks);
                            Analyse.addCarteJoueur(nouvelle_carte);
                            nouvelle_carte.setPosition(new Point(520, 280));
                            nouvelle_carte.setOpaque(false);
                            cont_cartes.add(nouvelle_carte);
                            nouvelle_carte.setVisible(true);

                            zone_points_joueur.setText("POINTS : " + Analyse.getPointsJoueur());
                            zone_compteur.setText("RC : " + Analyse.getCompte());

                            Analyse.newTour();

                            freeze = true;
                            timer_action = new Timer(1000, this);
                            timer_action.start();
                        }
                        else
                            zone_momo.setText("Tu ne peux pas doubler ta mise, tu n'as plus assez d'argent !");
                    }
                    else
                        zone_momo.setText("Tu ne peux pas effectuer cette action à ce stade de la partie !");
                }

                else if(event.getSource() == bouton_reste && !freeze)
                {
                    if(Analyse.isMiseFaite() && Analyse.getTour() >= 0)
                    {
                        zone_momo.setText("Tu choisis de ne rien faire de plus, une stratégie intéressante !");

                        Analyse.newTour();

                        freeze = true;
                        timer_action = new Timer(1000, this);
                        timer_action.start();
                    }
                    else
                        zone_momo.setText("Tu ne peux pas effectuer cette action à ce stade de la partie !");
                }

                else if(event.getSource() == bouton_split && !freeze)
                {
                    if(Analyse.isMiseFaite() && Analyse.getTour() == 0)
                    {
                        if(Analyse.isSplitPossible())
                        {
                            zone_momo.setText("SPLIT NON IMPLÉMENTÉ");

                            /*
                            Analyse.newTour();

                            freeze = true;
                            timer_action = new Timer(1000, this);
                            timer_action.start();
                            */
                        }
                        else
                            zone_momo.setText("Tu n'as pas de paires, le SPLIT n'est pas possible !");
                    }
                    else
                        zone_momo.setText("Tu ne peux pas effectuer cette action à ce stade de la partie !");
                }

                else if(event.getSource() == bouton_surrender && !freeze)
                {
                    if(Analyse.isMiseFaite() && Analyse.getTour() == 0)
                    {
                        zone_momo.setText("Tu te retires et récupères la moitié de ta mise, la sécurité avant tout !");

                        Analyse.addArgent((int)Math.ceil(Analyse.getMise() / 2.0));

                        Analyse.newTour();

                        cont_img_fin.setVisible(true);
                        freeze = true;
                        timer_restart = new Timer(3000, this);
                        timer_restart.start();
                    }
                    else
                        zone_momo.setText("Tu ne peux pas effectuer cette action à ce stade de la partie !");
                }
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Affichage conseils
     * @author Romain MONIER
     */
    public void affComptage()
    {
        String conseil = " Conseil : ";

        if(Parametres.isComptageActive())
        {
            switch(Analyse.getStrategie())
            {
                case RESTER:
                    conseil += "tu devrais RESTER !";
                    break;
                case TIRER:
                    conseil += "tu devrais TIRER !";
                    break;
                case DOUBLER:
                    conseil += "tu devrais DOUBLER !";
                    break;
                case PARTAGER:
                    conseil += "tu devrais SPLITTER !";
                    break;
                default:
                    conseil += "ERREUR";
            }

            zone_momo.append(conseil);
        }
    }

    /**
     * Event Timer
     * @author Romain MONIER
     * @param event l'event
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == timer_carte1)
        {
            timer_carte1.stop();
            Carte nouvelle_carte = null;
            try {
                nouvelle_carte = Analyse.getCarteAleatoire(decks);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Analyse.addCarteJoueur(nouvelle_carte);
            nouvelle_carte.setPosition(new Point(350, 280));
            nouvelle_carte.setOpaque(false);
            cont_cartes.add(nouvelle_carte);
            nouvelle_carte.setVisible(true);

            zone_points_joueur.setText("POINTS : " + Analyse.getPointsJoueur());
            zone_compteur.setText("RC : " + Analyse.getCompte());

            zone_momo.setText("Et de deux...");

            timer_carte2 = new Timer(1000, this);
            timer_carte2.start();
        }

        else if(event.getSource() == timer_carte2)
        {
            timer_carte2.stop();

            /*  CARTE CROUPIER  */

            Carte nouvelle_carte = null;
            try {
                nouvelle_carte = Analyse.getCarteAleatoire(decks);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Analyse.addCarteCroupier(nouvelle_carte);
            nouvelle_carte.setPosition(new Point(300, 50));
            nouvelle_carte.setOpaque(false);
            cont_cartes.add(nouvelle_carte);
            nouvelle_carte.setVisible(true);

            zone_points_croupier.setText("POINTS : " + Analyse.getPointsCroupier());
            zone_compteur.setText("RC : " + Analyse.getCompte());

            zone_momo.setText("Une pour moi...");

            timer_carte3 = new Timer(1000, this);
            timer_carte3.start();
        }

        else if(event.getSource() == timer_carte3)
        {
            timer_carte3.stop();
            if(Analyse.isJoueurBlackjack())
            {
                zone_momo.setText("Wow ! Blackjack !");
                timer_action = new Timer(2000, this);
                timer_action.start();
            }
            else
            {
                zone_momo.setText("Et voilà, les premières cartes sont distribuées ! Que veux-tu faire ?");
                affComptage();
                freeze = false;
            }
        }

        else if(event.getSource() == timer_action)
        {
            timer_action.stop();
            if(Analyse.getPointsJoueur() <= 21)
                zone_momo.append(" Maintenant à moi de tirer tant que j'ai moins de 17 points...");
            else if(Analyse.getPointsJoueur() == 21)
                zone_momo.append(" Voyons voir si j'ai mieux...");
            else
                zone_momo.append(" Laisse-moi tirer une carte que je vérifie si j'ai gagné...");

            Carte nouvelle_carte = null;
            try {
                nouvelle_carte = Analyse.getCarteAleatoire(decks);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Analyse.addCarteCroupier(nouvelle_carte);
            nouvelle_carte.setPosition(new Point(350, 50));
            nouvelle_carte.setOpaque(false);
            cont_cartes.add(nouvelle_carte);
            nouvelle_carte.setVisible(true);

            zone_points_croupier.setText("POINTS : " + Analyse.getPointsCroupier());
            zone_compteur.setText("RC : " + Analyse.getCompte());

            freeze = true;

            timer_carte_croupier = new Timer(1000, this);
            timer_carte_croupier.start();
        }

        else if(event.getSource() == timer_carte_croupier)
        {
            timer_carte_croupier.stop();

            for(int i = 50 ; Analyse.getPointsCroupier() < 17 && Analyse.getPointsJoueur() <= 21 ; i += 50)
            {
                Carte nouvelle_carte = null;
                try {
                    nouvelle_carte = Analyse.getCarteAleatoire(decks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Analyse.addCarteCroupier(nouvelle_carte);
                nouvelle_carte.setPosition(new Point(350+i, 50));
                nouvelle_carte.setOpaque(false);
                cont_cartes.add(nouvelle_carte);
                nouvelle_carte.setVisible(true);

                zone_points_croupier.setText("POINTS : " + Analyse.getPointsCroupier());
                zone_compteur.setText("RC : " + Analyse.getCompte());
            }
            timer_fin = new Timer(500, this);
            timer_fin.start();
        }

        else if(event.getSource() == timer_fin)
        {
            timer_fin.stop();

            ImageIcon img_fin = null;
            JLabel label_img_fin = null;
            Point pos_img_fin = new Point(0,0);

            Analyse.finPartie();

            cont_img_fin.setVisible(false);

            switch(Analyse.getResultat())
            {
                case EGALITE:
                    try {
                        img_fin = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo_egalite.png")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case GAGNE:
                    if(Analyse.isJoueurBlackjack()) {
                        try {
                            img_fin = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo_gagne_blackjack.png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            img_fin = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo_gagne.png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case PERDU:
                    if(Analyse.isCroupierBlackjack()) {
                        try {
                            img_fin = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo_perdu_blackjack.png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            img_fin = new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/backgrounds/momo_perdu.png")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    System.out.println("ERREUR");
            }

            label_img_fin = new JLabel(img_fin);
            label_img_fin.setBounds(pos_img_fin.x, pos_img_fin.y, img_fin.getIconWidth(), img_fin.getIconHeight());
            cont_img_fin.add(label_img_fin);
            cont_img_fin.setVisible(true);
            timer_restart = new Timer(3000, this);
            timer_restart.start();
        }

        else if(event.getSource() == timer_restart)
        {
            timer_restart.stop();

            String texte_momo;

            cont_img_fin.removeAll();
            cont_img_fin.setVisible(false);
            Analyse.resetGame();

            if(Analyse.getArgent() > 0)
            {
                texte_momo = "Bon aller, c'est reparti ! Mise ce que tu veux puis clique sur le paquet de cartes !";

                if(Parametres.isComptageActive())
                {
                    if(Analyse.getCompteRT(decks) > Analyse.getSeuilPositifRT(decks))
                        texte_momo += " Avec ce RC tu devrais beaucoup miser !";
                    else if(Analyse.getCompteRT(decks) < Analyse.getSeuilNegatifRT(decks))
                        texte_momo += " Avec ce RC tu devrais peu miser !";
                }
            }
            else
                texte_momo = "Zut ! Tu as tout perdu ! Pour avoir 100$ va dans Options et réinitialise ton argent !";

            zone_momo.setText(texte_momo);
            zone_points_croupier.setText("POINTS : " + Analyse.getPointsCroupier());
            zone_points_joueur.setText("POINTS : " + Analyse.getPointsJoueur());
            zone_compteur.setText("RC : " + Analyse.getCompte());
            zone_argent.setText("ARGENT : " + Analyse.getArgent() + "$");
            zone_mise.setText("MISE EN JEU\n" + Analyse.getMise() + "$");
            cont_cartes.removeAll();
            freeze = false;
        }
    }

    public void mouseEntered(MouseEvent event){}
    public void mouseExited(MouseEvent event){}
    public void mousePressed(MouseEvent event){}
    public void mouseReleased(MouseEvent event){}
}
