package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    private JFrame frame;

    public StartScreen(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // 시작 버튼 설정
        JButton startButton = new JButton("20234037\n KimHyojung \n Game Start");
        startButton.setFont(new Font("고딕", Font.BOLD, 30));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCharacterSelection();
            }
        });

        add(startButton, BorderLayout.CENTER);
    }

    // 캐릭터 선택 다이얼로그 표시
    private void showCharacterSelection() {
        String[] options = {"고양이", "강아지"};
        int playerChoice = JOptionPane.showOptionDialog(null, "캐릭터를 선택하시오 :", "캐릭터 선택",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // 다이얼로그가 닫힌 경우, 게임 종료
        if (playerChoice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        // 현재 내용 제거 후 선택된 캐릭터로 게임 패널 추가 및 프레임 갱신
        frame.getContentPane().removeAll();
        Game game = new Game(playerChoice + 1);
        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocus();
    }
}
