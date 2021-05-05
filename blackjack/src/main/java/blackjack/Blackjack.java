/* FICHIER BLACKJACK.JAVA :
 *      - FICHIER DE DÉMARRAGE 
 * 
 *  DERNIÈRE MÀJ : 04/05/2021 par ROMAIN MONIER
 *  CRÉÉ PAR ROMAIN MONIER
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE DE DEMARRAGE
 * ------------------------------------------
 */

package blackjack;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/** Création de la BDD, initialisation de l'audio, des paramètres et du tableau de stratégie puis lancement du menu
 * @author Romain MONIER
 */
public class Blackjack
{
    public static InputStream getResourceStream(String path) {
        return Objects.requireNonNull(Blackjack.class.getResourceAsStream("/" + path));
    }

    /** Démarrage du Jeu
     * @author Romain MONIER
     * @param args parametres (vides ici)
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        AppDirs appDirs = AppDirsFactory.getInstance();
        String app_dir = appDirs.getUserDataDir("Blackjack", "1.0.0", "Blackjack") + File.separator + "BLACKJACK/db";
        (new File(app_dir)).mkdirs();

        BaseDeDonnees bdd = new BaseDeDonnees("jdbc:sqlite:" + app_dir + "//save.db");
        
        Parametres.init(bdd);
        
        Tableau.init();
        
        Audio musique = new Audio("audio/music/musique.wav", true);
        
        Menus fenetre = new Menus(bdd, musique);
        
        fenetre.principal();    
        fenetre.setVisible(true);
    }
}

