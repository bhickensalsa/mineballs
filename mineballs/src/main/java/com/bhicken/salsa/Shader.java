package com.bhicken.salsa;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int programId;

    public Shader(String vertexPath, String fragmentPath) {
        String vertexCode = readFile(vertexPath);
        String fragmentCode = readFile(fragmentPath);

        int vertexShader = createShader(GL_VERTEX_SHADER, vertexCode);
        int fragmentShader = createShader(GL_FRAGMENT_SHADER, fragmentCode);

        programId = glCreateProgram();
        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        glLinkProgram(programId);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void use() {
        glUseProgram(programId);
    }

    public void setMat4(String name, Matrix4f matrix) {
        int location = glGetUniformLocation(programId, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            matrix.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        }
    }

    private String readFile(String path) {
        // Implement file reading logic here
        return "";
    }

    private int createShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        return shader;
    }
}