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
    comments = "Source: ChatMenu.proto")
public final class ChatHistoryServiceGrpc {

  private ChatHistoryServiceGrpc() {}

  public static final String SERVICE_NAME = "ChatHistoryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.generated.ChatMenu.ChatHistoryRequest,
      proto.generated.ChatMenu.ChatHistoryReply> getGetChatHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetChatHistory",
      requestType = proto.generated.ChatMenu.ChatHistoryRequest.class,
      responseType = proto.generated.ChatMenu.ChatHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.ChatMenu.ChatHistoryRequest,
      proto.generated.ChatMenu.ChatHistoryReply> getGetChatHistoryMethod() {
    io.grpc.MethodDescriptor<proto.generated.ChatMenu.ChatHistoryRequest, proto.generated.ChatMenu.ChatHistoryReply> getGetChatHistoryMethod;
    if ((getGetChatHistoryMethod = ChatHistoryServiceGrpc.getGetChatHistoryMethod) == null) {
      synchronized (ChatHistoryServiceGrpc.class) {
        if ((getGetChatHistoryMethod = ChatHistoryServiceGrpc.getGetChatHistoryMethod) == null) {
          ChatHistoryServiceGrpc.getGetChatHistoryMethod = getGetChatHistoryMethod = 
              io.grpc.MethodDescriptor.<proto.generated.ChatMenu.ChatHistoryRequest, proto.generated.ChatMenu.ChatHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "ChatHistoryService", "GetChatHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.ChatMenu.ChatHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.ChatMenu.ChatHistoryReply.getDefaultInstance()))
                  .setSchemaDescriptor(new ChatHistoryServiceMethodDescriptorSupplier("GetChatHistory"))
                  .build();
          }
        }
     }
     return getGetChatHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatHistoryServiceStub newStub(io.grpc.Channel channel) {
    return new ChatHistoryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatHistoryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ChatHistoryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatHistoryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ChatHistoryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ChatHistoryServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getChatHistory(proto.generated.ChatMenu.ChatHistoryRequest request,
        io.grpc.stub.StreamObserver<proto.generated.ChatMenu.ChatHistoryReply> responseObserver) {
      asyncUnimplementedUnaryCall(getGetChatHistoryMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetChatHistoryMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.ChatMenu.ChatHistoryRequest,
                proto.generated.ChatMenu.ChatHistoryReply>(
                  this, METHODID_GET_CHAT_HISTORY)))
          .build();
    }
  }

  /**
   */
  public static final class ChatHistoryServiceStub extends io.grpc.stub.AbstractStub<ChatHistoryServiceStub> {
    private ChatHistoryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatHistoryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatHistoryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatHistoryServiceStub(channel, callOptions);
    }

    /**
     */
    public void getChatHistory(proto.generated.ChatMenu.ChatHistoryRequest request,
        io.grpc.stub.StreamObserver<proto.generated.ChatMenu.ChatHistoryReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetChatHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatHistoryServiceBlockingStub extends io.grpc.stub.AbstractStub<ChatHistoryServiceBlockingStub> {
    private ChatHistoryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatHistoryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatHistoryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatHistoryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.generated.ChatMenu.ChatHistoryReply> getChatHistory(
        proto.generated.ChatMenu.ChatHistoryRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetChatHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatHistoryServiceFutureStub extends io.grpc.stub.AbstractStub<ChatHistoryServiceFutureStub> {
    private ChatHistoryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ChatHistoryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatHistoryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ChatHistoryServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_CHAT_HISTORY = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatHistoryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatHistoryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CHAT_HISTORY:
          serviceImpl.getChatHistory((proto.generated.ChatMenu.ChatHistoryRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.ChatMenu.ChatHistoryReply>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ChatHistoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatHistoryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.generated.ChatMenu.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatHistoryService");
    }
  }

  private static final class ChatHistoryServiceFileDescriptorSupplier
      extends ChatHistoryServiceBaseDescriptorSupplier {
    ChatHistoryServiceFileDescriptorSupplier() {}
  }

  private static final class ChatHistoryServiceMethodDescriptorSupplier
      extends ChatHistoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatHistoryServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChatHistoryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatHistoryServiceFileDescriptorSupplier())
              .addMethod(getGetChatHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
