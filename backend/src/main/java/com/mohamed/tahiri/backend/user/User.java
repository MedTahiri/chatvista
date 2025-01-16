package com.mohamed.tahiri.backend.user;

import com.mohamed.tahiri.backend.message.Message;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String image;

    @OneToMany(mappedBy = "senderUser")
    private List<Message> sendMessages;
    @OneToMany(mappedBy = "receiverUser")
    private List<Message> incomingMessages;

    public User() {

    }

    public User(String fullName, String email, String password, String image) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public List<Message> getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(List<Message> sendMessages) {
        this.sendMessages = sendMessages;
    }

    public List<Message> getIncomingMessages() {
        return incomingMessages;
    }

    public void setIncomingMessages(List<Message> incomingMessages) {
        this.incomingMessages = incomingMessages;
    }
}
