package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {

    private MoniteurImpl moniteur;
    private SportImpl sport;
    @BeforeEach
    void setUp() {
        moniteur = new MoniteurImpl("Jean");
        sport = Mockito.mock(SportImpl.class);
        Mockito.when(sport.contientMoniteur(moniteur)).thenReturn(true);
    }

    private boolean amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {
            StageImpl stage = mockitage(moniteur, i);
            if (!moniteur.ajouterStage(stage))
                return false;
        }
        return true;
    }

    private StageImpl mockitage(Moniteur moniteur, int i) {
        StageImpl stage = Mockito.mock(StageImpl.class);
        Mockito.when(stage.getMoniteur()).thenReturn(moniteur);
        Mockito.when(stage.getSport()).thenReturn(sport);
        Mockito.when(stage.getNumeroDeSemaine()).thenReturn(i);
        return stage;
    }

        private StageImpl mockitage(Moniteur moniteur, int i, SportImpl sport1) {
            StageImpl stage = Mockito.mock(StageImpl.class);
            Mockito.when(stage.getMoniteur()).thenReturn(moniteur);
            Mockito.when(stage.getSport()).thenReturn(sport1);
            Mockito.when(stage.getNumeroDeSemaine()).thenReturn(i);
            return stage;
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
        assertTrue(amenerALEtat(1, moniteur));
        StageImpl stage = mockitage(moniteur, 2);
        assertTrue(moniteur.ajouterStage(stage));
        assertEquals(2, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC3")
    void testMoniteurTC3() {
        assertTrue(amenerALEtat(2, moniteur));
        StageImpl stage = mockitage(moniteur, 3);
        assertTrue(moniteur.ajouterStage(stage));
        assertEquals(3, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC4")
    void testMoniteurTC4() {
        assertTrue(amenerALEtat(3, moniteur));
        StageImpl stage = mockitage(moniteur, 4);
        assertTrue(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC5")
    void testMoniteurTC5() {
        assertTrue(amenerALEtat(3, moniteur));
        StageImpl stage = mockitage(moniteur, 4);
        moniteur.ajouterStage(stage);
        StageImpl stage2 = mockitage(moniteur, 4);
        assertFalse(moniteur.ajouterStage(stage2));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC6")
    void testMoniteurTC6() {
        assertTrue(amenerALEtat(4, moniteur));
        StageImpl stage = mockitage(moniteur, 4);
        assertAll(
                () -> assertFalse(moniteur.contientStage(stage)),
                () -> assertFalse(moniteur.estLibre(4)),
                () -> assertFalse(moniteur.ajouterStage(stage)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    @DisplayName("TestMoniteurTC7")
    void testMoniteurTC7() {
        assertTrue(amenerALEtat(4, moniteur));
        StageImpl stage = mockitage(new MoniteurImpl("Marie"), 4);
        assertFalse(moniteur.ajouterStage(stage));
        assertEquals(4, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC8")
    void testMoniteurTC8() {
        assertTrue(amenerALEtat(4, moniteur));
        //not finished yet
        assertEquals(5, moniteur.nombreDeStages());
    }

    @Test
    @DisplayName("TestMoniteurTC9")
    void testMoniteurTC9() {
        StageImpl stage = mockitage(null, 1, new SportImpl("Mockitage"));
        assertFalse(moniteur.ajouterStage(stage));
    }
}