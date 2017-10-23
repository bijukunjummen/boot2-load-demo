package sample.load;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class MessageAck {

    private final String id;
    private final String received;
    private final String ack;

    @JsonCreator
    public MessageAck(
            @JsonProperty("id") String id,
            @JsonProperty("received") String received,
            @JsonProperty("ack") String ack
    ) {
        this.id = id;
        this.received = received;
        this.ack = ack;
    }

    public String getId() {
        return id;
    }

    public String getReceived() {
        return received;
    }

    public String getAck() {
        return ack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageAck that = (MessageAck) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(received, that.received) &&
                Objects.equals(ack, that.ack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, received, ack);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageAck{");
        sb.append("id='").append(id).append('\'');
        sb.append(", received='").append(received).append('\'');
        sb.append(", ack='").append(ack).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
