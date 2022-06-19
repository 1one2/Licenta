package images;

import proto.generated.Images;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageActions implements ImageSaving{

    private String imageFolder;

    public ImageActions() {
        this.imageFolder = "img";
    }
    @Override
    public void save(Images.ImageInfo imageInfo, ByteArrayOutputStream imageData) throws IOException {

        String imagePath;
        if(imageInfo.getImageUsage() == Images.ImageUsage.POST_PICTURE)
            imagePath = String.format("'%s'/'%s'_'%s'_'%s''%s'",
                imageFolder,imageInfo.getAccountId(),imageInfo.getPostId(),imageInfo.getImageUsage(),imageInfo.getImageType());
        else
            imagePath = String.format("'%s'/'%s'_'%s''%s'",
                imageFolder,imageInfo.getAccountId(),imageInfo.getImageUsage(),imageInfo.getImageType());

        imagePath = imagePath.replaceAll("'","");
        FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
        imageData.writeTo(fileOutputStream);
        fileOutputStream.close();


    }
}
