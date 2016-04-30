// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: packets.proto at 16:1
package au.com.codeka.warworlds.common.proto;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import java.io.IOException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import okio.ByteString;

/**
 *
 * The "hello" request when you first join the game contains some info about yourself which we
 * record for posterity.
 */
public final class HelloRequestPacket extends Message<HelloRequestPacket, HelloRequestPacket.Builder> {
  public static final ProtoAdapter<HelloRequestPacket> ADAPTER = new ProtoAdapter_HelloRequestPacket();

  private static final long serialVersionUID = 0L;

  public HelloRequestPacket() {
    this(ByteString.EMPTY);
  }

  public HelloRequestPacket(ByteString unknownFields) {
    super(ADAPTER, unknownFields);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof HelloRequestPacket;
  }

  @Override
  public int hashCode() {
    return unknownFields().hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    return builder.replace(0, 2, "HelloRequestPacket{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<HelloRequestPacket, Builder> {
    public Builder() {
    }

    @Override
    public HelloRequestPacket build() {
      return new HelloRequestPacket(buildUnknownFields());
    }
  }

  private static final class ProtoAdapter_HelloRequestPacket extends ProtoAdapter<HelloRequestPacket> {
    ProtoAdapter_HelloRequestPacket() {
      super(FieldEncoding.LENGTH_DELIMITED, HelloRequestPacket.class);
    }

    @Override
    public int encodedSize(HelloRequestPacket value) {
      return value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, HelloRequestPacket value) throws IOException {
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public HelloRequestPacket decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public HelloRequestPacket redact(HelloRequestPacket value) {
      Builder builder = value.newBuilder();
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}