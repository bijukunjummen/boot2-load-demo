package sample.load;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Message {

    private final String id;
    private final String payload;
    private final Long delay;

    @JsonCreator
    public Message(
            @JsonProperty("id") String id,
            @JsonProperty("payload") String payload,
            @JsonProperty("delay") Long delay
    ) {
        this.id = id;
        this.payload = payload;
        this.delay = delay;
    }


    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public Long getDelay() {
        return delay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(payload, message.payload) &&
                Objects.equals(delay, message.delay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payload, delay);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("id='").append(id).append('\'');
        sb.append(", payload='").append(payload).append('\'');
        sb.append(", delay=").append(delay);
        sb.append('}');
        return sb.toString();
    }
}
