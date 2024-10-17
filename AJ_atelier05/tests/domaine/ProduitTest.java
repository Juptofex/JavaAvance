package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;
    private Produit produit1;
    private Produit produit2;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 0.1);
        prixPub.definirPrix(3, 15);

        prixSolde = new Prix(TypePromo.SOLDE, 0.2);

        produit1 = new Produit("nom1", "marque1", "rayon1");
        produit1.ajouterPrix(LocalDate.now().minusDays(1), prixAucune);
        produit1.ajouterPrix(LocalDate.now().minusDays(3), prixPub);
        produit1.ajouterPrix(LocalDate.now().minusDays(4), prixSolde);

        produit2 = new Produit("nom1", "marque1", "rayon1");
    }

    @Test
    @DisplayName("Test du constructeur")
    void Produit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Produit(null, null, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Produit("", "", "");
        });
    }
    @Test
    @DisplayName("Test du getter de la marque")
    void getMarque() {
        assertEquals(produit1.getMarque(), "marque1");
    }

    @Test
    @DisplayName("Test du getter du nom")
    void getNom() {
        assertEquals(produit1.getNom(), "nom1");
    }

    @Test
    @DisplayName("Test du getter du rayon")
    void getRayon() {
        assertEquals(produit1.getRayon(), "rayon1");
    }

    @Test
    @DisplayName("Test de l'ajout de prix")
    void ajouterPrix() {
        assertThrows(IllegalArgumentException.class, () -> {
            produit1.ajouterPrix(null, prixAucune);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            produit1.ajouterPrix(LocalDate.now(), null);
        });
        assertThrows(DateDejaPresenteException.class, () -> {
            produit1.ajouterPrix(LocalDate.now().minusDays(1), prixAucune);
        });
        produit1.ajouterPrix(LocalDate.now().plusDays(1), prixAucune);
        assertEquals(prixAucune, produit1.getPrix(LocalDate.now().plusDays(1)));
    }

    @Test
    @DisplayName("Test du getter du prix")
    void getPrix() {
        assertThrows(PrixNonDisponibleException.class, () -> {
            produit1.getPrix(LocalDate.now().minusDays(5));
        });
        assertThrows(PrixNonDisponibleException.class, () -> {
            produit2.getPrix(LocalDate.now());
        });
        assertEquals(prixPub, produit1.getPrix(LocalDate.now().minusDays(2)));
    }

    @Test
    @DisplayName("Test du equals")
    void testequals() {
        assertEquals(produit1, produit2);
        assertNotEquals(produit1, new Produit("nom2", "marque1", "rayon1"));
        assertNotEquals(produit1, new Produit("nom1", "marque2", "rayon1"));
        assertNotEquals(produit1, new Produit("nom1", "marque1", "rayon2"));
    }

    @Test
    @DisplayName("Test du hashCode")
    void testHashCode() {
         assertEquals(produit1.hashCode(), produit2.hashCode());
    }
}