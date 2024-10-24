package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {

    private MoniteurImpl moniteur;
    private SportStub sport;
    @BeforeEach
    void setUp() {
        sport = new SportStub(true);
        moniteur = new MoniteurImpl("Jean");
    }

    private boolean amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {

            if (!moniteur.ajouterStage(new StageStub(i, sport, null)))
                return false;
        }
        return true;
    }
    @Test
    @DisplayName("TestMoniteurTC1")
    void testMoniteurTC1() {
        assertTrue(amenerALEtat(1, moniteur));
        assertEquals(1, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC2")
    void testMoniteurTC2() {
        assertTrue(amenerALEtat(2, moniteur));
        assertEquals(2, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC3")
    void testMoniteurTC3() {
        assertTrue(amenerALEtat(3, moniteur));
        assertEquals(3, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC4")
    void testMoniteurTC4() {
        assertTrue(amenerALEtat(4, moniteur));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC5")
    void testMoniteurTC5() {
        assertTrue(amenerALEtat(3, moniteur));
        StageStub stage = new StageStub(4, sport, null);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertEquals(3, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC6")
    void testMoniteurTC6() {
        assertTrue(amenerALEtat(2, moniteur));
        StageStub stage = new StageStub(3, sport, null);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertEquals(2, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC7")
    void testMoniteurTC7() {
        assertTrue(amenerALEtat(1, moniteur));
        StageStub stage = new StageStub(2, sport, null);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertEquals(1, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC8")
    void testMoniteurTC8() {
        StageStub stage = new StageStub(1, sport, null);
        moniteur.ajouterStage(stage);
        assertTrue(moniteur.supprimerStage(stage));
        assertEquals(0, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC9")
    void testMoniteurTC9() {
        assertTrue(amenerALEtat(3, moniteur));
        StageStub stage = new StageStub(4, sport, moniteur);
        moniteur.ajouterStage(stage);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC10")
    void testMoniteurTC10() {
        assertTrue(amenerALEtat(3, moniteur));
        StageStub stage = new StageStub(4, sport, moniteur);
        moniteur.ajouterStage(stage);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC11")
    void testMoniteurTC11() {
        assertTrue(amenerALEtat(4, moniteur));
        StageStub stage = new StageStub(5, sport, moniteur);
        assertFalse(moniteur.supprimerStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC12")
    void testMoniteurTC12() {
        assertTrue(amenerALEtat(4, moniteur));
        StageStub stage = new StageStub(5, sport, new MoniteurImpl("Marie"));
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC13")
    void testMoniteurTC13() {
        assertTrue(amenerALEtat(4, moniteur));
        StageStub stage = new StageStub(5, sport, moniteur);
        assertTrue(moniteur.ajouterStage(stage));
        assertEquals(5, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC14")
    void testMoniteurTC14() {
        assertTrue(amenerALEtat(4, moniteur));
        StageStub stage = new StageStub(5, new SportStub(false), null);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }
}