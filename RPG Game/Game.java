package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JPanel implements KeyListener {
    private Player player;
    private List<Monster> monsters;
    private int score;
    private Image monsterImage;
    private boolean gameOver;
    private Timer timer;

    public Game(int playerChoice) {
        Image playerImage;
        // 플레이어 및 몬스터 이미지 초기화
        if (playerChoice == 1) {
            ImageIcon playerIcon = new ImageIcon("image/cat.png");
            playerImage = playerIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            player = new Player(20, 20, playerImage);

            ImageIcon monsterIcon = new ImageIcon("image/mouse.png");
            monsterImage = monsterIcon.getImage();
        } else {
            ImageIcon playerIcon = new ImageIcon("image/dog.png");
            playerImage = playerIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            player = new Player(20, 20, playerImage);

            ImageIcon monsterIcon = new ImageIcon("image/food.png");
            monsterImage = monsterIcon.getImage();
        }

        monsters = new ArrayList<>();
        score = 0;
        gameOver = false;

        // 키 리스너 등록
        setFocusable(true);
        addKeyListener(this);

        // 타이머 설정
        timer = new Timer(100, e -> {
            if (!gameOver) {
                for (Monster monster : monsters) {
                    monster.move();
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(this::addMonsters); // 컴포넌트의 크기가 유효한지 보장된 후 몬스터 생성
    }

    // 게임에 몬스터 추가
    private void addMonsters() {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return; // 컴포넌트의 크기가 유효하지 않으면 몬스터를 추가하지 않음
        }

        Random rand = new Random();
        int numberOfMonsters = rand.nextInt(6) + 10; // 10~15마리 생성

        for (int i = 0; i < numberOfMonsters; i++) {
            int x = rand.nextInt(getWidth() - 70);
            int y = rand.nextInt(getHeight() - 70);

            Monster monster = new Monster(x, y, monsterImage);
            monsters.add(monster);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon background = new ImageIcon("image/Back.png");
        Image backgroundImage = background.getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 플레이어 그리기
        player.draw(g);

        // 몬스터들 그리기
        for (Monster monster : monsters) {
            monster.draw(g);
        }

        // 점수 표시
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);

        // 게임 종료 메시지 출력
        if (gameOver) {
            String gameOverMsg = "Finish";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(gameOverMsg)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(gameOverMsg, x, y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) return; // 게임 종료 시 키 입력 무시

        int keyCode = e.getKeyCode();
        int dx = 0, dy = 0;

        switch (keyCode) {
            case KeyEvent.VK_UP:
                dy = -5;
                break;
            case KeyEvent.VK_DOWN:
                dy = 5;
                break;
            case KeyEvent.VK_LEFT:
                dx = -5;
                player.faceLeft();
                break;
            case KeyEvent.VK_RIGHT:
                dx = 5;
                player.faceRight();
                break;
        }

        if (player.canMove(dx, dy)) {
            player.move(dx, dy);

            // 충돌 감지
            List<Monster> toRemove = new ArrayList<>();
            for (Monster monster : monsters) {
                if (player.getBounds().intersects(monster.getBounds())) {
                    toRemove.add(monster);
                    score++; // 점수 증가
                }
            }
            monsters.removeAll(toRemove);

            // 모든 몬스터가 제거되면 다음 라운드 선택
            if (monsters.isEmpty()) {
                int choice = JOptionPane.showOptionDialog(this, "다음 라운드로 진행하시겠습니까?", "게임 종료",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"다음 라운드", "종료"}, "다음 라운드");
                if (choice == JOptionPane.YES_OPTION) {
                    addMonsters();
                } else {
                    gameOver = true;
                    timer.stop(); // 타이머 중지
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
