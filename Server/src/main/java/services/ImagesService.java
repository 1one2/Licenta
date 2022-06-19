package services;

import com.google.protobuf.ByteString;
import images.ImageActions;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import proto.generated.ImageServiceGrpc;
import proto.generated.Images;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImagesService extends ImageServiceGrpc.ImageServiceImplBase {

    private Images.ImageInfo imageInfo;
    private ByteArrayOutputStream imageData;
    private String imageFolder = "img";
    private ImageActions saveImage = new ImageActions();
    @Override
    public StreamObserver<Images.ImageTransfer> uploadImage(StreamObserver<Images.Empty> responseObserver) {
        return new StreamObserver<Images.ImageTransfer>() {
            @Override
            public void onNext(Images.ImageTransfer uploadImageRequest) {
                if(uploadImageRequest.getDataCase() == Images.ImageTransfer.DataCase.IMAGE_INFO){
                    imageInfo = uploadImageRequest.getImageInfo();
                    imageData = new ByteArrayOutputStream();

                    System.out.println("uploadImage:onNext");
                    return;
                }
                else {
                    ByteString chunkData = uploadImageRequest.getChunkData();
                    if(imageData != null){
                        try {
                            chunkData.writeTo(imageData);
                        } catch (IOException e) {
                            return;
                        }
                    }
                }

            }

            @Override
            public void onError(Throwable throwable) {

                System.out.println("uploadImage:onError + " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {

                //save the image


                System.out.println("uploadImage:onCompleted-Beggining");
                try {
                    saveImage.save(imageInfo,imageData);

                    System.out.println("uploadImage:onCompleted-Try");
                } catch (IOException e) {

                    System.out.println("uploadImage:OnCompleted-Catch");
                    throw new RuntimeException(e);
                }
                responseObserver.onNext(Images.Empty.newBuilder().build());
                responseObserver.onCompleted();

                System.out.println("uploadImage:onNCompleted-End");
            }
        };
    }

    @Override
    public void downloadImage(Images.ImageInfo request, StreamObserver<Images.ImageTransfer> responseObserver) {

        ServerCallStreamObserver scso =(ServerCallStreamObserver)responseObserver;
        scso.setOnCancelHandler(() -> {
            System.out.println("The stream was cancelled");
        });

                String imagePath;
        if(request.getImageUsage() == Images.ImageUsage.POST_PICTURE)
            imagePath = String.format("'%s'/'%s'_'%s'_'%s''%s'",
                    imageFolder,request.getAccountId(),request.getPostId(),request.getImageUsage(),request.getImageType());
        else
            imagePath = String.format("'%s'/'%s'_'%s''%s'",
                    imageFolder,request.getAccountId(),request.getImageUsage(),request.getImageType());

        imagePath = imagePath.replaceAll("'","");

        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath);

            try {
                responseObserver.onNext(Images.ImageTransfer.newBuilder().setImageInfo(request).build());
                byte[] buffer =  new byte[1024]; // 1KB
                while (true) {
                    int n = fileInputStream.read(buffer);
                    if (n <= 0)
                        break;

                    Images.ImageTransfer response = Images.ImageTransfer.newBuilder()
                            .setChunkData(ByteString.copyFrom(buffer, 0, n))
                            .build();
                    responseObserver.onNext(response);
                }

            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.err.println("Something happened in the download image function");
            }
            responseObserver.onCompleted();
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + imagePath + " file not found");
        }
    }



}
