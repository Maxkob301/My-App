package Interfaces;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface ImageController {
     default Image getImage(String resultImageName){
        Image v = null;
        try {
            v = new Image(new FileInputStream("C:\\Users\\Маksim\\My-app\\My-App\\src\\main\\java\\Images\\" + resultImageName + ".jpg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return v;
    }
}
