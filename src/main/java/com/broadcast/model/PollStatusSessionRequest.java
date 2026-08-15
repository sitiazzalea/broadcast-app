package com.broadcast.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PollStatusSessionRequest {
    private List<String> sessionIds;
}
