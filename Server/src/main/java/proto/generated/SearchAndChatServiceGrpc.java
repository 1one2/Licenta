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
    comments = "Source: SearchAndChat.proto")
public final class SearchAndChatServiceGrpc {

  private SearchAndChatServiceGrpc() {}

  public static final String SERVICE_NAME = "SearchAndChatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.generated.SearchAndChat.SearchRequest,
      proto.generated.SearchAndChat.SearchReply> getSearchForPeopleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SearchForPeople",
      requestType = proto.generated.SearchAndChat.SearchRequest.class,
      responseType = proto.generated.SearchAndChat.SearchReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.SearchAndChat.SearchRequest,
      proto.generated.SearchAndChat.SearchReply> getSearchForPeopleMethod() {
    io.grpc.MethodDescriptor<proto.generated.SearchAndChat.SearchRequest, proto.generated.SearchAndChat.SearchReply> getSearchForPeopleMethod;
    if ((getSearchForPeopleMethod = SearchAndChatServiceGrpc.getSearchForPeopleMethod) == null) {
      synchronized (SearchAndChatServiceGrpc.class) {
        if ((getSearchForPeopleMethod = SearchAndChatServiceGrpc.getSearchForPeopleMethod) == null) {
          SearchAndChatServiceGrpc.getSearchForPeopleMethod = getSearchForPeopleMethod = 
              io.grpc.MethodDescriptor.<proto.generated.SearchAndChat.SearchRequest, proto.generated.SearchAndChat.SearchReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "SearchAndChatService", "SearchForPeople"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.SearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.SearchReply.getDefaultInstance()))
                  .setSchemaDescriptor(new SearchAndChatServiceMethodDescriptorSupplier("SearchForPeople"))
                  .build();
          }
        }
     }
     return getSearchForPeopleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.SearchAndChat.StartChatRequest,
      proto.generated.SearchAndChat.StartChatReply> getStartNewChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StartNewChat",
      requestType = proto.generated.SearchAndChat.StartChatRequest.class,
      responseType = proto.generated.SearchAndChat.StartChatReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.SearchAndChat.StartChatRequest,
      proto.generated.SearchAndChat.StartChatReply> getStartNewChatMethod() {
    io.grpc.MethodDescriptor<proto.generated.SearchAndChat.StartChatRequest, proto.generated.SearchAndChat.StartChatReply> getStartNewChatMethod;
    if ((getStartNewChatMethod = SearchAndChatServiceGrpc.getStartNewChatMethod) == null) {
      synchronized (SearchAndChatServiceGrpc.class) {
        if ((getStartNewChatMethod = SearchAndChatServiceGrpc.getStartNewChatMethod) == null) {
          SearchAndChatServiceGrpc.getStartNewChatMethod = getStartNewChatMethod = 
              io.grpc.MethodDescriptor.<proto.generated.SearchAndChat.StartChatRequest, proto.generated.SearchAndChat.StartChatReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SearchAndChatService", "StartNewChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.StartChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.StartChatReply.getDefaultInstance()))
                  .setSchemaDescriptor(new SearchAndChatServiceMethodDescriptorSupplier("StartNewChat"))
                  .build();
          }
        }
     }
     return getStartNewChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.SearchAndChat.RequestId,
      proto.generated.SearchAndChat.ReplyId> getGetAccountIdFromChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccountIdFromChat",
      requestType = proto.generated.SearchAndChat.RequestId.class,
      responseType = proto.generated.SearchAndChat.ReplyId.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.SearchAndChat.RequestId,
      proto.generated.SearchAndChat.ReplyId> getGetAccountIdFromChatMethod() {
    io.grpc.MethodDescriptor<proto.generated.SearchAndChat.RequestId, proto.generated.SearchAndChat.ReplyId> getGetAccountIdFromChatMethod;
    if ((getGetAccountIdFromChatMethod = SearchAndChatServiceGrpc.getGetAccountIdFromChatMethod) == null) {
      synchronized (SearchAndChatServiceGrpc.class) {
        if ((getGetAccountIdFromChatMethod = SearchAndChatServiceGrpc.getGetAccountIdFromChatMethod) == null) {
          SearchAndChatServiceGrpc.getGetAccountIdFromChatMethod = getGetAccountIdFromChatMethod = 
              io.grpc.MethodDescriptor.<proto.generated.SearchAndChat.RequestId, proto.generated.SearchAndChat.ReplyId>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SearchAndChatService", "GetAccountIdFromChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.RequestId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.ReplyId.getDefaultInstance()))
                  .setSchemaDescriptor(new SearchAndChatServiceMethodDescriptorSupplier("GetAccountIdFromChat"))
                  .build();
          }
        }
     }
     return getGetAccountIdFromChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.SearchAndChat.AccountRequest,
      proto.generated.Login.Account> getGetAccountInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAccountInfo",
      requestType = proto.generated.SearchAndChat.AccountRequest.class,
      responseType = proto.generated.Login.Account.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.SearchAndChat.AccountRequest,
      proto.generated.Login.Account> getGetAccountInfoMethod() {
    io.grpc.MethodDescriptor<proto.generated.SearchAndChat.AccountRequest, proto.generated.Login.Account> getGetAccountInfoMethod;
    if ((getGetAccountInfoMethod = SearchAndChatServiceGrpc.getGetAccountInfoMethod) == null) {
      synchronized (SearchAndChatServiceGrpc.class) {
        if ((getGetAccountInfoMethod = SearchAndChatServiceGrpc.getGetAccountInfoMethod) == null) {
          SearchAndChatServiceGrpc.getGetAccountInfoMethod = getGetAccountInfoMethod = 
              io.grpc.MethodDescriptor.<proto.generated.SearchAndChat.AccountRequest, proto.generated.Login.Account>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "SearchAndChatService", "GetAccountInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.SearchAndChat.AccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Login.Account.getDefaultInstance()))
                  .setSchemaDescriptor(new SearchAndChatServiceMethodDescriptorSupplier("GetAccountInfo"))
                  .build();
          }
        }
     }
     return getGetAccountInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SearchAndChatServiceStub newStub(io.grpc.Channel channel) {
    return new SearchAndChatServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SearchAndChatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SearchAndChatServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SearchAndChatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SearchAndChatServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SearchAndChatServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void searchForPeople(proto.generated.SearchAndChat.SearchRequest request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.SearchReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchForPeopleMethod(), responseObserver);
    }

    /**
     */
    public void startNewChat(proto.generated.SearchAndChat.StartChatRequest request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.StartChatReply> responseObserver) {
      asyncUnimplementedUnaryCall(getStartNewChatMethod(), responseObserver);
    }

    /**
     */
    public void getAccountIdFromChat(proto.generated.SearchAndChat.RequestId request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.ReplyId> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountIdFromChatMethod(), responseObserver);
    }

    /**
     */
    public void getAccountInfo(proto.generated.SearchAndChat.AccountRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Login.Account> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAccountInfoMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSearchForPeopleMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.SearchAndChat.SearchRequest,
                proto.generated.SearchAndChat.SearchReply>(
                  this, METHODID_SEARCH_FOR_PEOPLE)))
          .addMethod(
            getStartNewChatMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.SearchAndChat.StartChatRequest,
                proto.generated.SearchAndChat.StartChatReply>(
                  this, METHODID_START_NEW_CHAT)))
          .addMethod(
            getGetAccountIdFromChatMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.SearchAndChat.RequestId,
                proto.generated.SearchAndChat.ReplyId>(
                  this, METHODID_GET_ACCOUNT_ID_FROM_CHAT)))
          .addMethod(
            getGetAccountInfoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.SearchAndChat.AccountRequest,
                proto.generated.Login.Account>(
                  this, METHODID_GET_ACCOUNT_INFO)))
          .build();
    }
  }

  /**
   */
  public static final class SearchAndChatServiceStub extends io.grpc.stub.AbstractStub<SearchAndChatServiceStub> {
    private SearchAndChatServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchAndChatServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchAndChatServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchAndChatServiceStub(channel, callOptions);
    }

    /**
     */
    public void searchForPeople(proto.generated.SearchAndChat.SearchRequest request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.SearchReply> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSearchForPeopleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void startNewChat(proto.generated.SearchAndChat.StartChatRequest request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.StartChatReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartNewChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAccountIdFromChat(proto.generated.SearchAndChat.RequestId request,
        io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.ReplyId> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccountIdFromChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAccountInfo(proto.generated.SearchAndChat.AccountRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Login.Account> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAccountInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SearchAndChatServiceBlockingStub extends io.grpc.stub.AbstractStub<SearchAndChatServiceBlockingStub> {
    private SearchAndChatServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchAndChatServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchAndChatServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchAndChatServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.generated.SearchAndChat.SearchReply> searchForPeople(
        proto.generated.SearchAndChat.SearchRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSearchForPeopleMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.SearchAndChat.StartChatReply startNewChat(proto.generated.SearchAndChat.StartChatRequest request) {
      return blockingUnaryCall(
          getChannel(), getStartNewChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.SearchAndChat.ReplyId getAccountIdFromChat(proto.generated.SearchAndChat.RequestId request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountIdFromChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.Login.Account getAccountInfo(proto.generated.SearchAndChat.AccountRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAccountInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SearchAndChatServiceFutureStub extends io.grpc.stub.AbstractStub<SearchAndChatServiceFutureStub> {
    private SearchAndChatServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchAndChatServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchAndChatServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchAndChatServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.SearchAndChat.StartChatReply> startNewChat(
        proto.generated.SearchAndChat.StartChatRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStartNewChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.SearchAndChat.ReplyId> getAccountIdFromChat(
        proto.generated.SearchAndChat.RequestId request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccountIdFromChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.Login.Account> getAccountInfo(
        proto.generated.SearchAndChat.AccountRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAccountInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEARCH_FOR_PEOPLE = 0;
  private static final int METHODID_START_NEW_CHAT = 1;
  private static final int METHODID_GET_ACCOUNT_ID_FROM_CHAT = 2;
  private static final int METHODID_GET_ACCOUNT_INFO = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SearchAndChatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SearchAndChatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEARCH_FOR_PEOPLE:
          serviceImpl.searchForPeople((proto.generated.SearchAndChat.SearchRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.SearchReply>) responseObserver);
          break;
        case METHODID_START_NEW_CHAT:
          serviceImpl.startNewChat((proto.generated.SearchAndChat.StartChatRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.StartChatReply>) responseObserver);
          break;
        case METHODID_GET_ACCOUNT_ID_FROM_CHAT:
          serviceImpl.getAccountIdFromChat((proto.generated.SearchAndChat.RequestId) request,
              (io.grpc.stub.StreamObserver<proto.generated.SearchAndChat.ReplyId>) responseObserver);
          break;
        case METHODID_GET_ACCOUNT_INFO:
          serviceImpl.getAccountInfo((proto.generated.SearchAndChat.AccountRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.Login.Account>) responseObserver);
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

  private static abstract class SearchAndChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SearchAndChatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.generated.SearchAndChat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SearchAndChatService");
    }
  }

  private static final class SearchAndChatServiceFileDescriptorSupplier
      extends SearchAndChatServiceBaseDescriptorSupplier {
    SearchAndChatServiceFileDescriptorSupplier() {}
  }

  private static final class SearchAndChatServiceMethodDescriptorSupplier
      extends SearchAndChatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SearchAndChatServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SearchAndChatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SearchAndChatServiceFileDescriptorSupplier())
              .addMethod(getSearchForPeopleMethod())
              .addMethod(getStartNewChatMethod())
              .addMethod(getGetAccountIdFromChatMethod())
              .addMethod(getGetAccountInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
