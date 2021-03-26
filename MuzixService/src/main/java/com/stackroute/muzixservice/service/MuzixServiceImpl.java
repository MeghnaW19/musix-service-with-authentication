package com.stackroute.muzixservice.service;


import com.stackroute.muzixservice.domain.Track;
import com.stackroute.muzixservice.exception.TrackAlreadyExistsException;
import com.stackroute.muzixservice.exception.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MuzixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MuzixServiceImpl implements  MuzixService{


    private MuzixRepository muzixRepository;

    @Autowired
    public MuzixServiceImpl(MuzixRepository muzixRepository){
        this.muzixRepository = muzixRepository;
    }

    @Override
    public Track SaveTrackToWishList(Track track) throws TrackAlreadyExistsException {
        Optional optional = muzixRepository.findById(track.getTrackId());

        if(optional.isPresent())
        {
            throw new TrackAlreadyExistsException();
        }
        muzixRepository.insert(track);
        return track;
    }

    @Override
    public boolean deleteTrackFromWishList(String id) throws TrackNotFoundException {

        boolean status = true;
        Optional optional = muzixRepository.findById(id);
        if(optional.isPresent()){
            muzixRepository.deleteById(id);
            status= true;
        }
        else{
            throw new TrackNotFoundException();
        }

        return status;
    }

    @Override
    public Track updateCommentForTrack(String comments, String id) throws TrackNotFoundException {
        Track track = null;
        Optional optional = muzixRepository.findById(id);
        if(optional.isPresent()){
            track = muzixRepository.findById(id).get();
            track.setComments(comments);

            muzixRepository.save(track);

        }
        else
        {
            throw new TrackNotFoundException();
        }

        return track;

    }

    @Override
    public List<Track> getAllTrackFromWishList() throws Exception {
        return muzixRepository.findAll();
    }
}
