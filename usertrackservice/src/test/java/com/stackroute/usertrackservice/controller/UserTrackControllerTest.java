package com.stackroute.usertrackservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.stackroute.rabbitmq.config.Producer;
import com.rabbitmq.client.ConnectionFactory;
import com.stackroute.usertrackservice.config.Producer;
import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(UserTrackController.class)
public class UserTrackControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserTrackService userTrackService;

//  @Mock
//  Producer producer;
//
//  @Mock
//  RabbitTemplate rabbitTemplate;
//
//  @Mock
//  ConnectionFactory connectionFactory;




  private Artist artist;
  private Image image;
  private Track track;
  private User user;
  private List<Track> trackList;

  @Before
  public void setUp(){


    trackList = new ArrayList<>();
    image = new Image(1,"http:url","large");


    artist = new Artist(101,"Jonhn","new url",image);
    track = new Track("Track1","Mynewtrack","new comments","123","new track url",artist);

    trackList.add(track);
    image = new Image(2,"http:url","large");


    artist = new Artist(102,"Jonhnny","new url",image);
    track = new Track("Track2","Mynewtrack123","new comments updated","123","new track url",artist);
    trackList.add(track);
    user = new User("John","john@gmail.com",trackList);

  }

  @After
  public void tearDown(){

    image = null;
    artist = null;
    track=null;
    user=null;
  }

  //here any() is Mockito Argument Matchers – any()
  @Test
  public void testSaveTrackSuccess() throws Exception {

    when(userTrackService.saveUserTrackToWishList(any(),eq(user.getUsername()))).thenReturn(user);

    mockMvc.perform(post("/api/v1/usertrackservice/user/{username}/track",user.getUsername())

      .contentType(MediaType.APPLICATION_JSON)
        .content(jsonToString(track)))
      .andExpect(status().isCreated())
      .andDo(print());

    verify(userTrackService,times(1)).saveUserTrackToWishList(any(),eq(user.getUsername()));

  }
//
  @Test
  public void testSaveTrackFailure() throws Exception {

    when(userTrackService.saveUserTrackToWishList(any(),eq(user.getUsername()))).thenThrow(TrackAlreadyExistsException.class);
    mockMvc.perform(post("/api/v1/usertrackservice/user/{username}/track",user.getUsername())

      .contentType(MediaType.APPLICATION_JSON).
        content(jsonToString(track)))
      .andExpect(status().isConflict())
      .andDo(print());
    verify(userTrackService,times(1)).saveUserTrackToWishList(any(),eq(user.getUsername()));

  }
  @Test
  public void testUpdateCommentSuccess() throws Exception {

    when(userTrackService.updateCommentForTrack(track.getComments(),track.getTrackId(),user.getUsername())).thenReturn(user);
    mockMvc.perform(patch("/api/v1/usertrackservice/user/{username}/track",user.getUsername())
      .contentType(MediaType.APPLICATION_JSON).
        content(jsonToString(track)))
      .andExpect(status().isOk())
      .andDo(print());

    verify(userTrackService,times(1)).updateCommentForTrack(track.getComments(),track.getTrackId(),user.getUsername());
  }

  @Test
  public void testDeleteTrack() throws Exception {

    when(userTrackService.deleteUserTrackFromWishList(user.getUsername(),track.getTrackId())).thenReturn(user);
    mockMvc.perform(delete("/api/v1/usertrackservice/user/{username}/{trackId}",user.getUsername(),track.getTrackId())
      .contentType(MediaType.APPLICATION_JSON).
        content(jsonToString(track)))
      .andExpect(status().isOk())
      .andDo(print());

    verify(userTrackService,times(1)).deleteUserTrackFromWishList(user.getUsername(),track.getTrackId());
  }

  @Test
  public void getAllTrackFromWishList() throws Exception{

    when(userTrackService.getAllUserTrackFromWishList(user.getUsername())).thenReturn(trackList);
    mockMvc.perform(get("/api/v1/usertrackservice/user/{username}/tracks",user.getUsername())
      .contentType(MediaType.APPLICATION_JSON)
      .content(jsonToString(track)))
      .andExpect(status().isOk())
      .andDo(print());
    verify(userTrackService,times(1)).getAllUserTrackFromWishList(user.getUsername());
  }







  private static String jsonToString(final Object ob) throws JsonProcessingException {
    String result;

    try {
      ObjectMapper mapper = new ObjectMapper();
      String jsonContent = mapper.writeValueAsString(ob);
      result = jsonContent;
    } catch(JsonProcessingException e) {
      result = "JSON processing error";
    }

    return result;
  }
}

