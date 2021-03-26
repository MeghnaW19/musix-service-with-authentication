package com.stackroute.rabbitmq.domain;

import com.stackroute.rabbitmq.domain.TrackDTO;

import java.util.List;

public class UserDto {


  private String username;
  private String email;
  private String password;

  public List<TrackDTO> getTrackList() {
    return trackList;
  }

  public void setTrackList(List<TrackDTO> trackList) {
    this.trackList = trackList;
  }

  private List<TrackDTO> trackList;


  public UserDto() {
  }

  public UserDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  @Override
  public String toString() {
    return "UserDto{" +
      "username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
