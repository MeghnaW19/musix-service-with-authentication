package com.stackroute.usertrackservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND , reason = "Track not found")
public class TrackNotFoundException extends Exception {
}
