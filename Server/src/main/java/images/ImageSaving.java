package images;

import proto.generated.Images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ImageSaving {
    void save(Images.ImageInfo imageInfo, ByteArrayOutputStream imageData) throws IOException;
}
