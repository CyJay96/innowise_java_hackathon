package com.innowise.innowise_java_hackathon.model.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> extends Serializable {

    K getId();
}
