import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 97, 11, 12.1);
        GameProgress gameProgress2 = new GameProgress(95, 89, 21, 12.3);
        GameProgress gameProgress3 = new GameProgress(75, 63, 27, 9.3);
        File save1 = new File("src\\savegames\\save1.dat");
        File save2 = new File("src\\savegames\\save2.dat");
        File save3 = new File("src\\savegames\\save3.dat");
        File zip = new File("src\\savegames\\saves.zip");
        saveGame(save1, gameProgress1);
        saveGame(save2, gameProgress2);
        saveGame(save3, gameProgress3);
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip))) {
            addFileToZip(zout, save1);
            addFileToZip(zout, save2);
            addFileToZip(zout, save3);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        save1.delete();
        save2.delete();
        save3.delete();
    }

    public static void saveGame(File file, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(progress);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void addFileToZip(ZipOutputStream zout, File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry entry = new ZipEntry(file.getName());
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}