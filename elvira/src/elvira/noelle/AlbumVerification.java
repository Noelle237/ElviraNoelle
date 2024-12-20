package elvira.noelle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AlbumVerification {
    private static final String FILE_NAME = "albums.txt";

    public static List<Album> lireAlbums() throws IOException {
        List<Album> albums = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                albums.add(Album.fromString(line));
            }
        }

        return albums;
    }

    public static void ajouterAlbum(Album album) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(album.toString());
            writer.newLine();
        }
    }

    public static void supprimerAlbum(String titre) throws IOException {
        List<Album> albums = lireAlbums();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Album album : albums) {
                if (!album.getTitre().equalsIgnoreCase(titre)) {
                    writer.write(album.toString());
                    writer.newLine();
                }
            }
        }
    }

    public static void modifierAlbum(String ancienTitre, Album nouvelAlbum) throws IOException {
        List<Album> albums = lireAlbums();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Album album : albums) {
                if (album.getTitre().equalsIgnoreCase(ancienTitre)) {
                    writer.write(nouvelAlbum.toString());
                } else {
                    writer.write(album.toString());
                }
                writer.newLine();
            }
        }
    }
}