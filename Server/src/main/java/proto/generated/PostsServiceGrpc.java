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
    comments = "Source: Posts.proto")
public final class PostsServiceGrpc {

  private PostsServiceGrpc() {}

  public static final String SERVICE_NAME = "PostsService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.generated.Posts.PostRequest,
      proto.generated.Posts.Post> getGetPostsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetPosts",
      requestType = proto.generated.Posts.PostRequest.class,
      responseType = proto.generated.Posts.Post.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Posts.PostRequest,
      proto.generated.Posts.Post> getGetPostsMethod() {
    io.grpc.MethodDescriptor<proto.generated.Posts.PostRequest, proto.generated.Posts.Post> getGetPostsMethod;
    if ((getGetPostsMethod = PostsServiceGrpc.getGetPostsMethod) == null) {
      synchronized (PostsServiceGrpc.class) {
        if ((getGetPostsMethod = PostsServiceGrpc.getGetPostsMethod) == null) {
          PostsServiceGrpc.getGetPostsMethod = getGetPostsMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Posts.PostRequest, proto.generated.Posts.Post>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "PostsService", "GetPosts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.PostRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.Post.getDefaultInstance()))
                  .setSchemaDescriptor(new PostsServiceMethodDescriptorSupplier("GetPosts"))
                  .build();
          }
        }
     }
     return getGetPostsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Posts.LitePost,
      proto.generated.Posts.PostingReply> getPostItMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PostIt",
      requestType = proto.generated.Posts.LitePost.class,
      responseType = proto.generated.Posts.PostingReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.Posts.LitePost,
      proto.generated.Posts.PostingReply> getPostItMethod() {
    io.grpc.MethodDescriptor<proto.generated.Posts.LitePost, proto.generated.Posts.PostingReply> getPostItMethod;
    if ((getPostItMethod = PostsServiceGrpc.getPostItMethod) == null) {
      synchronized (PostsServiceGrpc.class) {
        if ((getPostItMethod = PostsServiceGrpc.getPostItMethod) == null) {
          PostsServiceGrpc.getPostItMethod = getPostItMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Posts.LitePost, proto.generated.Posts.PostingReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "PostsService", "PostIt"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.LitePost.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.PostingReply.getDefaultInstance()))
                  .setSchemaDescriptor(new PostsServiceMethodDescriptorSupplier("PostIt"))
                  .build();
          }
        }
     }
     return getPostItMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Posts.SendLike,
      proto.generated.Posts.PostingReply> getLikeItMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LikeIt",
      requestType = proto.generated.Posts.SendLike.class,
      responseType = proto.generated.Posts.PostingReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.Posts.SendLike,
      proto.generated.Posts.PostingReply> getLikeItMethod() {
    io.grpc.MethodDescriptor<proto.generated.Posts.SendLike, proto.generated.Posts.PostingReply> getLikeItMethod;
    if ((getLikeItMethod = PostsServiceGrpc.getLikeItMethod) == null) {
      synchronized (PostsServiceGrpc.class) {
        if ((getLikeItMethod = PostsServiceGrpc.getLikeItMethod) == null) {
          PostsServiceGrpc.getLikeItMethod = getLikeItMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Posts.SendLike, proto.generated.Posts.PostingReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "PostsService", "LikeIt"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.SendLike.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.PostingReply.getDefaultInstance()))
                  .setSchemaDescriptor(new PostsServiceMethodDescriptorSupplier("LikeIt"))
                  .build();
          }
        }
     }
     return getLikeItMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Posts.LiteCommentary,
      proto.generated.Posts.PostingReply> getSendCommentMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendComment",
      requestType = proto.generated.Posts.LiteCommentary.class,
      responseType = proto.generated.Posts.PostingReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.generated.Posts.LiteCommentary,
      proto.generated.Posts.PostingReply> getSendCommentMethod() {
    io.grpc.MethodDescriptor<proto.generated.Posts.LiteCommentary, proto.generated.Posts.PostingReply> getSendCommentMethod;
    if ((getSendCommentMethod = PostsServiceGrpc.getSendCommentMethod) == null) {
      synchronized (PostsServiceGrpc.class) {
        if ((getSendCommentMethod = PostsServiceGrpc.getSendCommentMethod) == null) {
          PostsServiceGrpc.getSendCommentMethod = getSendCommentMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Posts.LiteCommentary, proto.generated.Posts.PostingReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "PostsService", "SendComment"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.LiteCommentary.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.PostingReply.getDefaultInstance()))
                  .setSchemaDescriptor(new PostsServiceMethodDescriptorSupplier("SendComment"))
                  .build();
          }
        }
     }
     return getSendCommentMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.generated.Posts.CommentariesRequest,
      proto.generated.Posts.Commentary> getGetCommentsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetComments",
      requestType = proto.generated.Posts.CommentariesRequest.class,
      responseType = proto.generated.Posts.Commentary.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<proto.generated.Posts.CommentariesRequest,
      proto.generated.Posts.Commentary> getGetCommentsMethod() {
    io.grpc.MethodDescriptor<proto.generated.Posts.CommentariesRequest, proto.generated.Posts.Commentary> getGetCommentsMethod;
    if ((getGetCommentsMethod = PostsServiceGrpc.getGetCommentsMethod) == null) {
      synchronized (PostsServiceGrpc.class) {
        if ((getGetCommentsMethod = PostsServiceGrpc.getGetCommentsMethod) == null) {
          PostsServiceGrpc.getGetCommentsMethod = getGetCommentsMethod = 
              io.grpc.MethodDescriptor.<proto.generated.Posts.CommentariesRequest, proto.generated.Posts.Commentary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "PostsService", "GetComments"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.CommentariesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.generated.Posts.Commentary.getDefaultInstance()))
                  .setSchemaDescriptor(new PostsServiceMethodDescriptorSupplier("GetComments"))
                  .build();
          }
        }
     }
     return getGetCommentsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PostsServiceStub newStub(io.grpc.Channel channel) {
    return new PostsServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PostsServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PostsServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PostsServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PostsServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class PostsServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getPosts(proto.generated.Posts.PostRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.Post> responseObserver) {
      asyncUnimplementedUnaryCall(getGetPostsMethod(), responseObserver);
    }

    /**
     */
    public void postIt(proto.generated.Posts.LitePost request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnimplementedUnaryCall(getPostItMethod(), responseObserver);
    }

    /**
     */
    public void likeIt(proto.generated.Posts.SendLike request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnimplementedUnaryCall(getLikeItMethod(), responseObserver);
    }

    /**
     */
    public void sendComment(proto.generated.Posts.LiteCommentary request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSendCommentMethod(), responseObserver);
    }

    /**
     */
    public void getComments(proto.generated.Posts.CommentariesRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.Commentary> responseObserver) {
      asyncUnimplementedUnaryCall(getGetCommentsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetPostsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.Posts.PostRequest,
                proto.generated.Posts.Post>(
                  this, METHODID_GET_POSTS)))
          .addMethod(
            getPostItMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.Posts.LitePost,
                proto.generated.Posts.PostingReply>(
                  this, METHODID_POST_IT)))
          .addMethod(
            getLikeItMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.Posts.SendLike,
                proto.generated.Posts.PostingReply>(
                  this, METHODID_LIKE_IT)))
          .addMethod(
            getSendCommentMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.generated.Posts.LiteCommentary,
                proto.generated.Posts.PostingReply>(
                  this, METHODID_SEND_COMMENT)))
          .addMethod(
            getGetCommentsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                proto.generated.Posts.CommentariesRequest,
                proto.generated.Posts.Commentary>(
                  this, METHODID_GET_COMMENTS)))
          .build();
    }
  }

  /**
   */
  public static final class PostsServiceStub extends io.grpc.stub.AbstractStub<PostsServiceStub> {
    private PostsServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PostsServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PostsServiceStub(channel, callOptions);
    }

    /**
     */
    public void getPosts(proto.generated.Posts.PostRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.Post> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetPostsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void postIt(proto.generated.Posts.LitePost request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPostItMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void likeIt(proto.generated.Posts.SendLike request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getLikeItMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendComment(proto.generated.Posts.LiteCommentary request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendCommentMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getComments(proto.generated.Posts.CommentariesRequest request,
        io.grpc.stub.StreamObserver<proto.generated.Posts.Commentary> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetCommentsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PostsServiceBlockingStub extends io.grpc.stub.AbstractStub<PostsServiceBlockingStub> {
    private PostsServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PostsServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PostsServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<proto.generated.Posts.Post> getPosts(
        proto.generated.Posts.PostRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetPostsMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.Posts.PostingReply postIt(proto.generated.Posts.LitePost request) {
      return blockingUnaryCall(
          getChannel(), getPostItMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.Posts.PostingReply likeIt(proto.generated.Posts.SendLike request) {
      return blockingUnaryCall(
          getChannel(), getLikeItMethod(), getCallOptions(), request);
    }

    /**
     */
    public proto.generated.Posts.PostingReply sendComment(proto.generated.Posts.LiteCommentary request) {
      return blockingUnaryCall(
          getChannel(), getSendCommentMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<proto.generated.Posts.Commentary> getComments(
        proto.generated.Posts.CommentariesRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetCommentsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PostsServiceFutureStub extends io.grpc.stub.AbstractStub<PostsServiceFutureStub> {
    private PostsServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PostsServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PostsServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PostsServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.Posts.PostingReply> postIt(
        proto.generated.Posts.LitePost request) {
      return futureUnaryCall(
          getChannel().newCall(getPostItMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.Posts.PostingReply> likeIt(
        proto.generated.Posts.SendLike request) {
      return futureUnaryCall(
          getChannel().newCall(getLikeItMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.generated.Posts.PostingReply> sendComment(
        proto.generated.Posts.LiteCommentary request) {
      return futureUnaryCall(
          getChannel().newCall(getSendCommentMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_POSTS = 0;
  private static final int METHODID_POST_IT = 1;
  private static final int METHODID_LIKE_IT = 2;
  private static final int METHODID_SEND_COMMENT = 3;
  private static final int METHODID_GET_COMMENTS = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PostsServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PostsServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_POSTS:
          serviceImpl.getPosts((proto.generated.Posts.PostRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.Posts.Post>) responseObserver);
          break;
        case METHODID_POST_IT:
          serviceImpl.postIt((proto.generated.Posts.LitePost) request,
              (io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply>) responseObserver);
          break;
        case METHODID_LIKE_IT:
          serviceImpl.likeIt((proto.generated.Posts.SendLike) request,
              (io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply>) responseObserver);
          break;
        case METHODID_SEND_COMMENT:
          serviceImpl.sendComment((proto.generated.Posts.LiteCommentary) request,
              (io.grpc.stub.StreamObserver<proto.generated.Posts.PostingReply>) responseObserver);
          break;
        case METHODID_GET_COMMENTS:
          serviceImpl.getComments((proto.generated.Posts.CommentariesRequest) request,
              (io.grpc.stub.StreamObserver<proto.generated.Posts.Commentary>) responseObserver);
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

  private static abstract class PostsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PostsServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.generated.Posts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PostsService");
    }
  }

  private static final class PostsServiceFileDescriptorSupplier
      extends PostsServiceBaseDescriptorSupplier {
    PostsServiceFileDescriptorSupplier() {}
  }

  private static final class PostsServiceMethodDescriptorSupplier
      extends PostsServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PostsServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PostsServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PostsServiceFileDescriptorSupplier())
              .addMethod(getGetPostsMethod())
              .addMethod(getPostItMethod())
              .addMethod(getLikeItMethod())
              .addMethod(getSendCommentMethod())
              .addMethod(getGetCommentsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
