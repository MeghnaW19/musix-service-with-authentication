package com.stackroute.usertrackservice.service;

import com.stackroute.usertrackservice.domain.Track;
//import User;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserTrackService {

     User saveUserTrackToWishList(Track track , String username) throws TrackAlreadyExistsException;

     User deleteUserTrackFromWishList(String username , String trackId) throws TrackNotFoundException;

     User updateCommentForTrack(String comments , String trackId , String username) throws TrackNotFoundException;

     List<Track> getAllUserTrackFromWishList(String username) throws Exception;

     User registerUser(User user) throws UserAlreadyExistsException;
    // Users updateuser(Users users) throws UserAlreadyExistsException;


}
