package test;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Monster {
    private int x, y; // 몬스터 좌표
    private Image image; // 몬스터 이미지
    private int dx, dy; // 몬스터 이동 방향

    public Monster(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
        Random rand = new Random();
        this.dx = rand.nextInt(5) - 2; // -2 to 2
        this.dy = rand.nextInt(5) - 2; // -2 to 2
    }

    // 화면에 몬스터 그리기
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    // 몬스터 이동
    public void move() {
        x += dx;
        y += dy;

        // 벽에 부딪히면 방향 반전
        if (x < 0 || x > 800 - image.getWidth(null)) dx = -dx;
        if (y < 0 || y > 600 - image.getHeight(null)) dy = -dy;
    }

    // 충돌 감지를 위한 몬스터의 경계 사각형 반환
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
}
