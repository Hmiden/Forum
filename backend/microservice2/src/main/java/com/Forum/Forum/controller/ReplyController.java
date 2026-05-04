package com.Forum.Forum.controller;

import com.Forum.Forum.Services.IServiceReply;
import com.Forum.Forum.Services.ImplServiceReply;
import com.Forum.Forum.entity.Reply;
import com.Forum.Forum.entity.ReplyDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private IServiceReply replyService;

    @PostMapping("/topic/{topicId}")
    public ReplyDTO create(@RequestBody Reply reply,
                           @PathVariable Long topicId) {
        return replyService.create(reply, topicId);
    }



    @GetMapping("/topic/{topicId}")
    public List<Reply> getByTopic(@PathVariable Long topicId) {
        return replyService.getByTopic(topicId);
    }

    @PutMapping("/{id}")
    public Reply update(@PathVariable Long id, @RequestBody Reply reply) {
        return replyService.update(id, reply);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        replyService.delete(id);
    }
}
