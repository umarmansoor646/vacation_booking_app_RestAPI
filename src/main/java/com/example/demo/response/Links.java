package com.example.demo.response;

import lombok.Data;

@Data
public class Links {
    private Self self;
    public Links() {
    	self = new Self();
    }
}

