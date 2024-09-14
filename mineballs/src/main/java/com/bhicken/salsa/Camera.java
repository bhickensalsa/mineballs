package com.bhicken.salsa;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private float yaw;
    private float pitch;
    private float fov;
    private float aspectRatio;
    private float sensitivity;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Camera(int width, int height) {
        position = new Vector3f(0.0f, 0.0f, 3.0f);
        front = new Vector3f(0.0f, 0.0f, -1.0f);
        up = new Vector3f(0.0f, 1.0f, 0.0f);
        yaw = -90.0f;
        pitch = 0.0f;
        fov = 45.0f;
        aspectRatio = (float) width / height;
        sensitivity = 0.1f;

        projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(fov), aspectRatio, 0.1f, 100.0f);
        viewMatrix = new Matrix4f();
        updateCameraVectors();
    }

    public void updateViewMatrix() {
        viewMatrix.identity();
        viewMatrix.lookAt(position, position.add(front, new Vector3f()), up);
    }

    public void processKeyboardInput(long window) {
        float cameraSpeed = 0.05f;
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
            position.add(front.mul(cameraSpeed, new Vector3f()));
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
            position.sub(front.mul(cameraSpeed, new Vector3f()));
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            position.sub(front.cross(up, new Vector3f()).normalize().mul(cameraSpeed));
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
            position.add(front.cross(up, new Vector3f()).normalize().mul(cameraSpeed));
    }

    public void processMouseInput(double xoffset, double yoffset) {
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (pitch > 89.0f)
            pitch = 89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;
        updateCameraVectors();
    }

    private void updateCameraVectors() {
        Vector3f direction = new Vector3f();
        direction.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        direction.y = (float) Math.sin(Math.toRadians(pitch));
        direction.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
        front = direction.normalize();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
}