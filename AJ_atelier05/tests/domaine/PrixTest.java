package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 0.1);
        prixPub.definirPrix(3, 15);

        prixSolde = new Prix(TypePromo.SOLDE, 0.2);
    }

    @Test
    @DisplayName("Test du constructeur")
    void Prix() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix(null, 0.2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Prix(TypePromo.SOLDE, -0.5);
        });
    }

    @Test
    @DisplayName("Test du getter des types de promos")
    void getTypePromo() {
        assertEquals(prixAucune.getTypePromo(), null);
        assertEquals(prixPub.getTypePromo(), TypePromo.PUB);
    }

    @Test
    @DisplayName("Test du getter des valeurs des promos")
    void getValeurPromo() {
        assertEquals(prixAucune.getValeurPromo(), 0);
        assertEquals(prixPub.getValeurPromo(), 0.3);
    }

    @Test
    @DisplayName("Test de la definition du prix")
    void definirPrix() {
        assertThrows(IllegalArgumentException.class, () -> {
            prixAucune.definirPrix(0, 12);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            prixAucune.definirPrix(4, 0);
        });
        prixAucune.definirPrix(10, 6);
        assertEquals(6, prixAucune.getPrix(10));
    }

    @ParameterizedTest
    @DisplayName("Test du getter du prix")
    @ValueSource(ints = {10, 15, 20, 25})
    void getPrix(int qty) {
        assertThrows(IllegalArgumentException.class, () -> {
           prixAucune.getPrix(0);
        });
        assertEquals(20, prixAucune.getPrix(1));
        assertEquals(20, prixAucune.getPrix(5));
        assertEquals(20, prixAucune.getPrix(9));
        assertEquals(10, prixAucune.getPrix(qty));
        assertThrows(QuantiteNonAutoriseeException.class, () -> {
            prixPub.getPrix(2);
        });
        assertThrows(QuantiteNonAutoriseeException.class, () -> {
            prixSolde.getPrix(1);
        });
    }
}