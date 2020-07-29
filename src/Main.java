import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        String[] gamesaves = {"C://Games/savedgames/save1.dat",
                "C://Games/savedgames/save2.dat",
                "C://Games/savedgames/save3.dat"};

        openZip("C://Games/savedgames/saves.zip");

        for (String gameSave: gamesaves) {
            System.out.println(openProgress(gameSave));
        }
    }


    public static void openZip (String filePath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(name);
                    while (zis.available()>0){
                        fos.write(zis.read());
                    }
                fos.flush();
                fos.close();
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress (String filepath){
        GameProgress gameProgress = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));) {
            try {
                gameProgress = (GameProgress) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return gameProgress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameProgress;
    }
}
