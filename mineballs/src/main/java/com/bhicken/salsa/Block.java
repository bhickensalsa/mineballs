package com.bhicken.salsa;

import org.joml.Vector3f;

public class Block {
    private Vector3f position;
    private int textureId;

    public Block(Vector3f position, int textureId) {
        this.position = position;
        this.textureId = textureId;
    }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextureId() {
        return textureId;
    }
}