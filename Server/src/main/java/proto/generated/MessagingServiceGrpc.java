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
    comments = "Source: Messaging.proto")
public final class MessagingServiceGrpc {

  private MessagingServiceGrpc() {}

  public static final String SERVICE_NAME = "MessagingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.generated.Messaging.MessageHistoryRequest,
      proto.generated.Messaging.Message> getGetMessagingHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMessagingHistory",
      requestType = proto.generated.Messaging.MessageHistoryRequest.class,
      responseType = proto.generated.Messaging.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Messaging.MessageHistoryRequest,
      proto.generated.Messaging.Message> getGetMessagingHistoryMethod() {
    io.grpc.MethodDescriptor<proto.generated.Messaging.MessageHistoryRequest, proto.generated.Messaging.Message> getGetMessagingHistoryMethod;
    if ((getGetMessagingHistoryMethod = MessagingServiceGrpc.getGetMessagingHistoryMethod) == null) {
      synchronized (MessagingServiceGrpc.class) {
        if ((getGetMessagingHistoryMethod = MessagingServiceGrpc.getGetMessagingHistoryMethod) == null) {
          MessagingServiceGrpc.getGetMessagingHistoryMethod = getGetMessagingHistoryMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Messaging.MessageHistoryRequest, proto.generated.Messaging.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "MessagingService", "GetMessagingHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Messaging.MessageHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Messaging.Message.getDefaultInstance()))
                  .setSchemaDescriptor(new MessagingServiceMethodDescriptorSupplier("GetMessagingHistory"))
                  .build();
          }
        }
     }
     return getGetMessagingHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Messaging.Message,
      proto.generated.Messaging.MessageFromServer> getSendReceiveMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendReceiveMessage",
      requestType = proto.generated.Messaging.Message.class,
      responseType = proto.generated.Messaging.MessageFromServer.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Messaging.Message,
      proto.generated.Messaging.MessageFromServer> getSendReceiveMessageMethod() {
    io.grpc.MethodDescriptor<proto.generated.Messaging.Message, proto.generated.Messaging.MessageFromServer> getSendReceiveMessageMethod;
    if ((getSendReceiveMessageMethod = MessagingServiceGrpc.getSendReceiveMessageMethod) == null) {
      synchronized (MessagingServiceGrpc.class) {
        if ((getSendReceiveMessageMethod = MessagingServiceGrpc.getSendReceiveMessageMethod) == null) {
          MessagingServiceGrpc.getSendReceiveMessageMethod = getSendReceiveMessageMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Messaging.Message, proto.generated.Messaging.MessageFromServer>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "MessagingService", "SendReceiveMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Messaging.Message.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Messaging.MessageFromServer.getDefaultInstance()))
                  .setSchemaDescriptor(new MessagingServiceMethodDescriptorSupplier("SendReceiveMessage"))
                  .build();
          }
        }
     }
     return getSendReceiveMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessagingServiceStub newStub(io.grpc.Channel channel) {
    return new MessagingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessagingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MessagingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessagingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MessagingServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MessagingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getMessagingHistory(proto.generated.Messaging.MessageHistoryRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Messaging.Message> responseObserver) {
      asyncUnimplementedUnaryCall(getGetMessagingHistoryMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<proto.generated.Messaging.Message> sendReceiveMessage(
        io.grpc.stub.StreamObserver<proto.generated.Messaging.MessageFromServer> responseObserver) {
      return asyncUnimplementedStreamingCall(getSendReceiveMessageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMessagingHistoryMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.Messaging.MessageHistoryRequest,
                proto.generated.Messaging.Message>(
                  this, METHODID_GET_MESSAGING_HISTORY)))
          .addMethod(
            getSendReceiveMessageMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                proto.generated.Messaging.Message,
                proto.generated.Messaging.MessageFromServer>(
                  this, METHODID_SEND_RECEIVE_MESSAGE)))
          .build();
    }
  }

  /**
   */
  public static final class MessagingServiceStub extends io.grpc.stub.AbstractStub<MessagingServiceStub> {
    private MessagingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessagingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessagingServiceStub(channel, callOptions);
    }

    /**
     */
    public void getMessagingHistory(proto.generated.Messaging.MessageHistoryRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Messaging.Message> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetMessagingHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<proto.generated.Messaging.Message> sendReceiveMessage(
        io.grpc.stub.StreamObserver<proto.generated.Messaging.MessageFromServer> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendReceiveMessageMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class MessagingServiceBlockingStub extends io.grpc.stub.AbstractStub<MessagingServiceBlockingStub> {
    private MessagingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessagingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessagingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.generated.Messaging.Message> getMessagingHistory(
        proto.generated.Messaging.MessageHistoryRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetMessagingHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessagingServiceFutureStub extends io.grpc.stub.AbstractStub<MessagingServiceFutureStub> {
    private MessagingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MessagingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MessagingServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_MESSAGING_HISTORY = 0;
  private static final int METHODID_SEND_RECEIVE_MESSAGE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessagingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessagingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MESSAGING_HISTORY:
          serviceImpl.getMessagingHistory((proto.generated.Messaging.MessageHistoryRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.Messaging.Message>) responseObserver);
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
        case METHODID_SEND_RECEIVE_MESSAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendReceiveMessage(
              (io.grpc.stub.StreamObserver<proto.generated.Messaging.MessageFromServer>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MessagingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessagingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.generated.Messaging.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MessagingService");
    }
  }

  private static final class MessagingServiceFileDescriptorSupplier
      extends MessagingServiceBaseDescriptorSupplier {
    MessagingServiceFileDescriptorSupplier() {}
  }

  private static final class MessagingServiceMethodDescriptorSupplier
      extends MessagingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MessagingServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MessagingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessagingServiceFileDescriptorSupplier())
              .addMethod(getGetMessagingHistoryMethod())
              .addMethod(getSendReceiveMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
