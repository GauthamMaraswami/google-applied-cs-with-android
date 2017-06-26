package com.google.engedu.wordladder;

import java.util.ArrayList;

/**
 * Created by gautham on 20/6/17.
 */

public class GraphNode {
    String word;
    ArrayList<String> neighbours;
    GraphNode(String s)
    {
        word=s;
        neighbours=new ArrayList<>();
    }
}
