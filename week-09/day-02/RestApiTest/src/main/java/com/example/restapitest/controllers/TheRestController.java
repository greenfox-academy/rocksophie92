package com.example.restapitest.controllers;

import com.example.restapitest.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TheRestController {

  @GetMapping(value = "/doubling")
  public ResponseEntity<Object> doublingEndpoint(@RequestParam(value = "input", required = false) Integer receivedNum) {
    if (receivedNum != null) {
      Values values = new Values(receivedNum);
      return ResponseEntity.ok().body(values);
    } else {
      ErrorObject errorObject = new ErrorObject("Please provide an input!");
      return ResponseEntity.ok().body(errorObject);
    }
  }

  @GetMapping(value = "/greeter")
  public ResponseEntity<Object> greeterEndpoint(@RequestParam(value = "name", required = false) String receivedName,
                                                @RequestParam(value = "title", required = false) String receivedTitle) {
    if (receivedTitle == null) {
      ErrorObject errorObject = new ErrorObject("Please provide a title!");
      return ResponseEntity.ok().body(errorObject);
    } else if (receivedName == null) {
      ErrorObject errorObject = new ErrorObject("Please provide a name!");
      return ResponseEntity.ok().body(errorObject);
    } else {
      Message message = new Message("Oh, hi there " + receivedName + ", my dear " + receivedTitle + "!");
      return ResponseEntity.ok().body(message);
    }
  }

  @GetMapping(value = "/appenda/{appendable}")
  public ResponseEntity<Object> appendAEndpoint(@PathVariable(value = "appendable") String appendable) {
    return new ResponseEntity<>(new Append(appendable + "a"), HttpStatus.OK);
  }

  @PostMapping(value = "/dountil/{what}")
  public ResponseEntity<Object> doUntil(@PathVariable(value = "what") String what, @RequestBody DoUntil doUntil) {
    Integer input = doUntil.getUntil();
    if (what.equals("sum")) {
      return new ResponseEntity<>(new DoUntilSum(input), HttpStatus.OK);
    } else if (what.equals("factor")) {
      return new ResponseEntity<>(new DoUntilFactor(input), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new ErrorObject("Please provide an input!"), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "arrays")
  public ResponseEntity<ArraysResponse> arraysEndpoint(@RequestBody ArraysRequestMessage arraysRequestMessage) {
    if (arraysRequestMessage.getNumbers() != null || arraysRequestMessage.getWhat() == null) {
      if (arraysRequestMessage.getWhat().equals("sum")) {
        int result = 0;
        for (int i = 0; i < arraysRequestMessage.getNumbers().length; i++) {
          result += arraysRequestMessage.getNumbers()[i];
        }
        return new ResponseEntity<>(new ArraysReturnMessageSum(result), HttpStatus.OK);
      } else if (arraysRequestMessage.getWhat().equals("multiply")) {
        int multiplyResult = 1;
        for (int i = 0; i < arraysRequestMessage.getNumbers().length; i++) {
          multiplyResult *= arraysRequestMessage.getNumbers()[i];
        }
        return new ResponseEntity<>(new ArraysReturnMessageSum(multiplyResult), HttpStatus.OK);
      } else if (arraysRequestMessage.getWhat().equals("double")) {
        int[] result = new int[arraysRequestMessage.getNumbers().length];
        for (int i = 0; i < arraysRequestMessage.getNumbers().length; i++) {
          result[i] = (arraysRequestMessage.getNumbers()[i] * 2);
        }
        return new ResponseEntity<>(new ArrayReturnMessage(result), HttpStatus.OK);
      }
    } else {
      return new ResponseEntity<>(new ArraysErrorMessage("Please provide what to do with the numbers!"), HttpStatus.BAD_REQUEST);
    } return new ResponseEntity<>(new ArraysResponse(), HttpStatus.OK);
  }
}

