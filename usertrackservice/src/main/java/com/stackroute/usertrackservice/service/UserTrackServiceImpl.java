package com.stackroute.usertrackservice.service;
//
//import com.stackroute.rabbitmq.config.Producer;
import com.stackroute.rabbitmq.domain.UserDto;

import com.stackroute.usertrackservice.config.Producer;
import com.stackroute.usertrackservice.domain.Track;
import com.stackroute.usertrackservice.domain.User;
import com.stackroute.usertrackservice.exception.TrackAlreadyExistsException;
import com.stackroute.usertrackservice.exception.TrackNotFoundException;
import com.stackroute.usertrackservice.exception.UserAlreadyExistsException;
import com.stackroute.usertrackservice.repository.UserTrackRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTrackServiceImpl implements UserTrackService {

  private Producer producer ;

  @Autowired
  private RabbitTemplate amqpTemplate;

//  @Autowired
//  private DirectExchange directExchange;

//  private String routingkey="userroutingKey";
//  private String exchangeName="userexchange";
//  private String routingTrackKey = "trackroutingkey";


    private UserTrackRepository userTrackRepository;
    private User user;
    private UserDto userDto;

//  @Autowired
//  private RabbitTemplate amqpTemplate;

    @Autowired
    public UserTrackServiceImpl(UserTrackRepository userTrackRepository , Producer producer)
    {
        this.userTrackRepository = userTrackRepository;
       this.producer = producer;
    }




    @Override

    public User registerUser(User user) throws UserAlreadyExistsException {

       userDto = new UserDto();
      userDto.setUsername(user.getUsername());
      userDto.setEmail(user.getEmail());
      userDto.setPassword(user.getPassword());




      System.out.println("In service 1111" + user);

        User fetcheduserobj = userTrackRepository.findByUsername(user.getUsername());
        if(fetcheduserobj != null){
            throw new UserAlreadyExistsException();
        }
        else {
          this.user = userTrackRepository.save(user);

               //sendToRabbitMq(userDto);
          producer.sendToRabbitMq(userDto);
//            amqpTemplate.convertAndSend(exchangeName, routingkey, userDto);
            System.out.println("Send msg = " + user);
          }

        return this.user;
    }



//  @RabbitListener(queues = "registeruser")
//    public void publishToRabbitMq(){
//    User user = null;
//    try {
//      user = registerUser(this.user);
//      System.out.println("User after register is " + this.user);
//
//      if(user !=null){
//
//      }
//
//    } catch (UserAlreadyExistsException e) {
//      e.printStackTrace();
//    }
//
//
//    }


    @Override
    public User saveUserTrackToWishList(Track track, String username) throws TrackAlreadyExistsException {

        User fetchUser = userTrackRepository.findByUsername(username);

        List<Track> fetchTracks = fetchUser.getTrackList();


        if(fetchTracks != null){

            //fetchTracks = fetchUser.getTrackList();

            for (Track trackobj : fetchTracks
            ) {

                if (trackobj.getTrackId().equals(track.getTrackId()))
                {

                    throw new TrackAlreadyExistsException();

                }
            }



            fetchTracks.add(track);
            fetchUser.setTrackList(fetchTracks);

          userDto = new UserDto();
          userDto.setUsername(username);
          userDto.setEmail(fetchUser.getEmail());

          List list  = new ArrayList<>();
          list.add(fetchTracks);

          //  TrackDTO trackDTO = new TrackDTO();
          // List list = user.getTrackList();
          userDto.setTrackList(list);
        //  sendToRabbitMqData(userDto);
         producer.sendToRabbitMqTrackObject(userDto);
         // amqpTemplate.convertAndSend(directExchange.getName(), "track_routing", userDto);

            userTrackRepository.save(fetchUser);
        }

        else{

            fetchTracks = new ArrayList<>();
            fetchTracks.add(track);
            fetchUser.setTrackList(fetchTracks);
             userDto = new UserDto();
            userDto.setUsername(fetchUser.getUsername());
            userDto.setEmail(fetchUser.getEmail());
           // userDto.setPassword(fetchUser.getPassword());
            List list  = new ArrayList<>();
            list.add(fetchTracks);
          System.out.println("list with databbbbbbbbbbbbbbbbbb" + list);
        //  TrackDTO trackDTO = new TrackDTO();
           // List list = user.getTrackList();
            userDto.setTrackList(list);
          System.out.println("userDto databbbbbbbbbbbbbbbbbbbbb" + userDto);

//           sendToRabbitMqData(userDto);
            userTrackRepository.save(fetchUser);
          producer.sendToRabbitMqTrackObject(userDto);

        }

        return fetchUser;


    }

//    public void sendToRabbitMqData(UserDto userDto){
//      amqpTemplate.convertAndSend(directExchange.getName(), "track_routing", userDto);
//    }



    @Override
    public User deleteUserTrackFromWishList(String username , String trackId) throws TrackNotFoundException {

        User fetchUser = userTrackRepository.findByUsername( username);
        List<Track>  fetchTracks = fetchUser.getTrackList();

        if(fetchTracks.size()>0) {

            for (int i = 0; i < fetchTracks.size(); i++
            ) {
                if (trackId.equals(fetchTracks.get(i).getTrackId())) {
                    fetchTracks.remove(i);

                    fetchUser.setTrackList(fetchTracks);
                    userTrackRepository.save(fetchUser);
                    break;

                }

            }

        }
        else{
            throw new TrackNotFoundException();

        }
        return fetchUser;

    }

    @Override
    public User updateCommentForTrack(String comments, String trackId, String username) throws TrackNotFoundException {

        User fetchUser = userTrackRepository.findByUsername(username);
        List<Track> fetchList = fetchUser.getTrackList();
        if(fetchList.size()>0)
        {
            for(int i=0;i<fetchList.size();i++)
            {
                if(trackId.equals(fetchList.get(i).getTrackId())){
                    fetchList.get(i).setComments(comments);

                    userTrackRepository.save(fetchUser);
                    break;
                }
            }

        }
        else {
            throw new TrackNotFoundException();
        }

        return fetchUser;


    }

    @Override
    public List<Track> getAllUserTrackFromWishList(String username) throws Exception {
        User user = userTrackRepository.findByUsername(username);
        return user.getTrackList();
    }




}
