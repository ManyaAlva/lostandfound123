package com.campus.lostfound.service;

import com.campus.lostfound.model.Item;
import java.util.List;

/** Program to an interface: the AI engine can be swapped (e.g. for an LLM) without touching the controller. */
public interface MatchingService {
    List<Match> findMatches(Item item);
}
