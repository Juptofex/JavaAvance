import java.util.ArrayList;

public class PizzaComposee extends Pizza {

    public static int REMISE = 15;

    public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
        super(titre, description, ingredients);
    }

    public boolean ajouter() {
        throw new UnsupportedOperationException("Impossible d'ajouter un ingrédient à une pizza composée");
    }

    public boolean supprimer() {
        throw new UnsupportedOperationException("Impossible de supprimer un ingrédient à une pizza composée");
    }

    public double calculerPrix() {
        return Math.ceil(super.calculerPrix() * (100 - REMISE) / 100);
    }

}
