package com.stackroute.usertrackservice.controller;




import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;
import com.stackroute.usertrackservice.service.UserTrackService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/usertrackservice")
public class UserTrackController {

    private UserTrackService userTrackService;
    private ResponseEntity responseEntity ;



    public UserTrackController() {
    }

    @Autowired
    public UserTrackController(UserTrackService userTrackService){

        this.userTrackService = userTrackService;
    }


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody User user) throws UserAlreadyExistsException {


        try {
             User userdata = userTrackService.registerUser(user);
             if(userdata == null){
               responseEntity = new ResponseEntity<>("Try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
             }
            responseEntity = new ResponseEntity<User>(user, HttpStatus.CREATED);

        } catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException();
        }


        return responseEntity;

    }

    @PostMapping("/user/{username}/track")
    public ResponseEntity<?> saveUserTrackToWishList(@RequestBody Track track , @PathVariable("username") String username) throws TrackAlreadyExistsException {
        try {

            User user = userTrackService.saveUserTrackToWishList(track,username);
            responseEntity = new ResponseEntity(user , HttpStatus.CREATED);

        } catch (TrackAlreadyExistsException e) {
            throw new TrackAlreadyExistsException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping("user/{username}/{trackId}")
    public ResponseEntity<?> deleteUserTrackFromWishList(@PathVariable ("username") String username , @PathVariable("trackId") String trackid) throws TrackNotFoundException {


        try {

            User user = userTrackService.deleteUserTrackFromWishList(username,trackid);
            responseEntity = new ResponseEntity(user, HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            throw new TrackNotFoundException();
        }
        catch (Exception exception){
            responseEntity = new ResponseEntity( exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }


    @PatchMapping("user/{username}/track")
    public ResponseEntity<?> updateCommentForUserTrack(@RequestBody Track track , @PathVariable ("username") String username) throws TrackNotFoundException {

        try{
            User user = userTrackService.updateCommentForTrack(track.getComments() , track.getTrackId() , username);
            System.out.println("coming from test case" + user);
            responseEntity = new ResponseEntity(track , HttpStatus.OK);
        } catch (TrackNotFoundException e) {
            System.out.println("coming from test case1111");
            throw new TrackNotFoundException();
        }
        catch (Exception e)
        {
            responseEntity = new ResponseEntity(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }


    @GetMapping("user/{username}/tracks")
    public ResponseEntity<?> getAllUserTrackFromWishList(@PathVariable ("username") String username){

        try{

            responseEntity = new ResponseEntity(userTrackService.getAllUserTrackFromWishList(username), HttpStatus.OK);

        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }
}
