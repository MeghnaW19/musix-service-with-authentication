package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.config.Producer;
import com.stackroute.usertrackservice.domain.Artist;
import com.stackroute.usertrackservice.domain.Image;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.repository.UserTrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class UserTrackServiceTest {

  @Mock
  private UserTrackRepository userTrackRepository;
  @Mock
  private Producer producer;

  private User user;
  private Track track;
  private Artist artist;
  private List<Track> list;

  @InjectMocks
  UserTrackServiceImpl userTrackService;


  @Before
  public void setUp(){
    MockitoAnnotations.initMocks(this);

    Image image = new Image(1,"http:url","large");


    artist = new Artist(101,"Jonhn","new url",image);
    track = new Track("Track1","Mynewtrack","new comments","123","new track url",artist);
    list = new ArrayList<>();
    list.add(track);

    user  = new User("John123" ,"john@gmail.com",list);
  }

  @After
  public void tearDown(){

    userTrackRepository.deleteAll();
    list = null;
    artist = null;
    track = null;
    user =null;

  }

  @Test
  public void testSaveUserTrackSucces() throws TrackAlreadyExistsException {
 user  = new User("John156" , "john@gmail.com",null);
    when(userTrackRepository.findByUsername(user.getUsername())).thenReturn(user);
    User fetchedUser = userTrackService.saveUserTrackToWishList(track,user.getUsername());
    Assert.assertEquals(fetchedUser,user);
    verify(userTrackRepository,timeout(1)).findByUsername(user.getUsername());
    verify(userTrackRepository,times(1)).save(user);
  }

  @Test
  public void testdeleteUserTrackFromWishList() throws TrackNotFoundException {
    when(userTrackRepository.findByUsername(user.getUsername())).thenReturn(user);
    User fetchedUser = userTrackService.deleteUserTrackFromWishList(user.getUsername(),track.getTrackId());
    Assert.assertEquals(fetchedUser,user);
    verify(userTrackRepository,times(1)).findByUsername(user.getUsername());
    verify(userTrackRepository,times(1)).save(user);
  }

  @Test
  public void testupdateCommentForTrack() throws TrackNotFoundException {
    when(userTrackRepository.findByUsername(user.getUsername())).thenReturn(user);
    User fetchedUser = userTrackService.updateCommentForTrack("new updated comments" , track.getTrackId(),user.getUsername());
    Assert.assertEquals(fetchedUser.getTrackList().get(0).getComments() , "new updated comments");
    verify(userTrackRepository,times(1)).findByUsername(user.getUsername());
    verify(userTrackRepository,times(1)).save(user);
  }

  @Test
  public void testgetAllUserTrackFromWishList() throws Exception {
    when(userTrackRepository.findByUsername(user.getUsername())).thenReturn(user);
    List <Track> fetchedlist = userTrackService.getAllUserTrackFromWishList(user.getUsername());
    Assert.assertEquals(fetchedlist,list);
    verify(userTrackRepository,times(1)).findByUsername(user.getUsername());
  }
}
