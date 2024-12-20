package elvira.noelle;


public class Album {
    private String titre;
    private String artiste;

    public Album(String titre, String artiste) {
        this.titre = titre;
        this.artiste = artiste;
    }

    public String getTitre() {
        return titre;
    }

    public String getArtiste() {
        return artiste;
    }

    @Override
    public String toString() {
        return titre + ";" + artiste;
    }

    public static Album fromString(String line) {
        String[] parts = line.split(";");
        return new Album(parts[0], parts[1]);
    }
}

