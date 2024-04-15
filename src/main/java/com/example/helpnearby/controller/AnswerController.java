package com.example.helpnearby.controller;

import com.example.helpnearby.dto.CompleteAnswerDto;
import com.example.helpnearby.dto.CreateAnswerDto;
import com.example.helpnearby.dto.GetAnswerDto;
import com.example.helpnearby.services.AnswerService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answer")
    @ApiResponse(
            responseCode = "201",
            description = "The order has been created",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<?> addAnswer(@RequestBody GetAnswerDto answer) {
        return ResponseEntity.ok(answerService.save(answer));
    }

    @GetMapping("/answers")
    @ApiResponse(
            responseCode = "200",
            description = "The list of all answers",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<?> findAllAnswers() {
        return ResponseEntity.ok(answerService.findAll());
    }

    @PostMapping("/answer/complete")
    @ApiResponse(
            responseCode = "200",
            description = "The answer has been completed",
            content = @Content(mediaType = "application/json")
    )
    public ResponseEntity<?> completeAnswer(@RequestBody CompleteAnswerDto answer) {
        answerService.completeAnswer(answer);
        return ResponseEntity.ok("Успешно завершено!");
    }
}
