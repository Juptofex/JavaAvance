import java.util.ArrayList;
import java.util.Iterator;

public abstract class Pizza {

    public static double PRIX_BASE = 5.0;
    private String titre, description;
    private ArrayList<Ingredient> ingredients;


    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this.titre = titre;
        this.description = description;
        this.ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            if (this.ingredients.contains(ingredient)) {
                throw new IllegalArgumentException("Il ne peut pas y avoir deux fois le même ingrédient dans une pizza");
            }
            this.ingredients.add(ingredient);
        }
    }

    public Pizza(String titre, String description) {
        this(titre, description, new ArrayList<Ingredient>());
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public boolean ajouter(Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            throw new IllegalArgumentException("L'ingrédient " + ingredient.getNom() + " est déjà présent dans la pizza, il ne peut pas y avoir deux fois le même ingrédient dans une pizza");
        }
        ingredients.add(ingredient);
        return true;
    }

    public boolean supprimer(Ingredient ingredient) {
        if (!ingredients.contains(ingredient)) {
            throw new IllegalArgumentException("L'ingrédient " + ingredient.getNom() + " n'est pas présent dans la pizza, il ne peut pas être supprimé");
        }
        ingredients.remove(ingredient);
        return true;
    }

    public double calculerPrix() {
        double prix = PRIX_BASE;
        for (Ingredient ingredient : ingredients) {
            prix += ingredient.getPrix();
        }
        return prix;
    }

    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }
}
