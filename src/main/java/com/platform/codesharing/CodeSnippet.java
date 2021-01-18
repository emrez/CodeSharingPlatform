package com.platform.codesharing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@ResponseBody
@Entity(name = "code_snippet")
@Table//(name ="CodeSnippet")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CodeSnippet extends EmptyJsonResponse {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @JsonIgnore // TODO Remove these JsonIgnores
    private final String uuid;

    @Column(length = 40000)
    private String code;

    @CreatedDate
    @GeneratedValue(strategy = GenerationType.AUTO)
    private LocalDateTime date;

    @LastModifiedDate
    private LocalDateTime updatedOn;

    private Long time;
    private Long views;

    boolean isSecretByTime;
    boolean isSecretByView;
    boolean secret;


    public CodeSnippet() {
        uuid = UUID.randomUUID().toString();
    }


    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }


    public boolean isAllowed() {
        return time > 0 || views > 0 || !isSecret();

    }

    public Long getTimePasses() {
        LocalDateTime tmp = updatedOn;
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(tmp, now);
        return duration.getSeconds();
    }


    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", updatedOn=" + updatedOn +
                ", time=" + time +
                ", views=" + views +
                ", isSecretByTime=" + isSecretByTime +
                ", isSecretByView=" + isSecretByView +
                ", secret=" + secret +
                '}';
    }
}
