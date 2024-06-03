package test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player {
    private int x, y; // 플레이어 좌표
    private Image image; // 플레이어 이미지
    private boolean facingRight; // 플레이어가 오른쪽을 향하는지 여부

    public Player(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.facingRight = true; // 기본값은 오른쪽을 향함
    }

    // 화면에 플레이어 그리기
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        if (!facingRight) {
            at.scale(-1, 1);
            at.translate(-image.getWidth(null), 0);
        }
        g2d.drawImage(image, at, null);
    }

    // 플레이어 이동
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    // 플레이어가 이동할 수 있는지 확인
    public boolean canMove(int dx, int dy) {
        return true;
    }

    // 플레이어가 왼쪽을 향하도록 설정
    public void faceLeft() {
        facingRight = false;
    }

    // 플레이어가 오른쪽을 향하도록 설정
    public void faceRight() {
        facingRight = true;
    }

    // 충돌 감지를 위한 플레이어의 경계 사각형 반환
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
}
