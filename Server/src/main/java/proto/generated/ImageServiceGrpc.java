package proto.generated;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: Images.proto")
public final class ImageServiceGrpc {

  private ImageServiceGrpc() {}

  public static final String SERVICE_NAME = "ImageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.generated.Images.ImageTransfer,
      proto.generated.Images.Empty> getUploadImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UploadImage",
      requestType = proto.generated.Images.ImageTransfer.class,
      responseType = proto.generated.Images.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Images.ImageTransfer,
      proto.generated.Images.Empty> getUploadImageMethod() {
    io.grpc.MethodDescriptor<proto.generated.Images.ImageTransfer, proto.generated.Images.Empty> getUploadImageMethod;
    if ((getUploadImageMethod = ImageServiceGrpc.getUploadImageMethod) == null) {
      synchronized (ImageServiceGrpc.class) {
        if ((getUploadImageMethod = ImageServiceGrpc.getUploadImageMethod) == null) {
          ImageServiceGrpc.getUploadImageMethod = getUploadImageMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Images.ImageTransfer, proto.generated.Images.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "ImageService", "UploadImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Images.ImageTransfer.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Images.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new ImageServiceMethodDescriptorSupplier("UploadImage"))
                  .build();
          }
        }
     }
     return getUploadImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Images.ImageInfo,
      proto.generated.Images.ImageTransfer> getDownloadImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadImage",
      requestType = proto.generated.Images.ImageInfo.class,
      responseType = proto.generated.Images.ImageTransfer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Images.ImageInfo,
      proto.generated.Images.ImageTransfer> getDownloadImageMethod() {
    io.grpc.MethodDescriptor<proto.generated.Images.ImageInfo, proto.generated.Images.ImageTransfer> getDownloadImageMethod;
    if ((getDownloadImageMethod = ImageServiceGrpc.getDownloadImageMethod) == null) {
      synchronized (ImageServiceGrpc.class) {
        if ((getDownloadImageMethod = ImageServiceGrpc.getDownloadImageMethod) == null) {
          ImageServiceGrpc.getDownloadImageMethod = getDownloadImageMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Images.ImageInfo, proto.generated.Images.ImageTransfer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "ImageService", "DownloadImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Images.ImageInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Images.ImageTransfer.getDefaultInstance()))
                  .setSchemaDescriptor(new ImageServiceMethodDescriptorSupplier("DownloadImage"))
                  .build();
          }
        }
     }
     return getDownloadImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ImageServiceStub newStub(io.grpc.Channel channel) {
    return new ImageServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ImageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ImageServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ImageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ImageServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ImageServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<proto.generated.Images.ImageTransfer> uploadImage(
        io.grpc.stub.StreamObserver<proto.generated.Images.Empty> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadImageMethod(), responseObserver);
    }

    /**
     */
    public void downloadImage(proto.generated.Images.ImageInfo request,
        io.grpc.stub.StreamObserver<proto.generated.Images.ImageTransfer> responseObserver) {
      asyncUnimplementedUnaryCall(getDownloadImageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadImageMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                proto.generated.Images.ImageTransfer,
                proto.generated.Images.Empty>(
                  this, METHODID_UPLOAD_IMAGE)))
          .addMethod(
            getDownloadImageMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.Images.ImageInfo,
                proto.generated.Images.ImageTransfer>(
                  this, METHODID_DOWNLOAD_IMAGE)))
          .build();
    }
  }

  /**
   */
  public static final class ImageServiceStub extends io.grpc.stub.AbstractStub<ImageServiceStub> {
    private ImageServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<proto.generated.Images.ImageTransfer> uploadImage(
        io.grpc.stub.StreamObserver<proto.generated.Images.Empty> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getUploadImageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void downloadImage(proto.generated.Images.ImageInfo request,
        io.grpc.stub.StreamObserver<proto.generated.Images.ImageTransfer> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getDownloadImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ImageServiceBlockingStub extends io.grpc.stub.AbstractStub<ImageServiceBlockingStub> {
    private ImageServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.generated.Images.ImageTransfer> downloadImage(
        proto.generated.Images.ImageInfo request) {
      return blockingServerStreamingCall(
          getChannel(), getDownloadImageMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ImageServiceFutureStub extends io.grpc.stub.AbstractStub<ImageServiceFutureStub> {
    private ImageServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ImageServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ImageServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ImageServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_DOWNLOAD_IMAGE = 0;
  private static final int METHODID_UPLOAD_IMAGE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ImageServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ImageServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DOWNLOAD_IMAGE:
          serviceImpl.downloadImage((proto.generated.Images.ImageInfo) request,
              (io.grpc.stub.StreamObserver<proto.generated.Images.ImageTransfer>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD_IMAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.uploadImage(
              (io.grpc.stub.StreamObserver<proto.generated.Images.Empty>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ImageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ImageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.generated.Images.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ImageService");
    }
  }

  private static final class ImageServiceFileDescriptorSupplier
      extends ImageServiceBaseDescriptorSupplier {
    ImageServiceFileDescriptorSupplier() {}
  }

  private static final class ImageServiceMethodDescriptorSupplier
      extends ImageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ImageServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ImageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ImageServiceFileDescriptorSupplier())
              .addMethod(getUploadImageMethod())
              .addMethod(getDownloadImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
