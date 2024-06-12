import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawingFrame extends JFrame {
    private JLabel la; // 중앙에 위치할 레이블
    private int x, y; // 클릭한 위치의 좌표
    private String selectedShape = "Circle"; // 초기 선택 모양을 Circle로 설정
    private ArrayList<Shape> shapes = new ArrayList<>(); // 도형 정보를 저장할 리스트

    public DrawingFrame() {
        // 프레임 설정
        setTitle("그리기 프레임"); // 창의 제목 설정
        setSize(500, 500); // 창의 크기 설정

        // 콤보박스 생성 및 초기화
        JComboBox<String> cb = new JComboBox<>();
        cb.addItem("Circle"); // 원 항목 추가
        cb.addItem("Rectangle"); // 사각형 항목 추가
        cb.addItem("Triangle"); // 삼각형 항목 추가

        // 콤보박스의 선택이 바뀔 때 호출되는 액션 리스너 추가
        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 선택된 모양을 업데이트
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                selectedShape = (String) combo.getSelectedItem();
            }
        });

        // 지우개 버튼 생성
        JButton eraseButton = new JButton("지우개");
        // 지우개 버튼 클릭 시 호출되는 액션 리스너 추가
        eraseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapes.clear(); // 도형 리스트를 비움
                repaint(); // 화면 다시 그리기
            }
        });

        // 콤보박스와 지우개 버튼을 포함하는 패널 생성
        JPanel topPanel = new JPanel();
        topPanel.add(cb); // 패널에 콤보박스 추가
        topPanel.add(eraseButton); // 패널에 지우개 버튼 추가

        // 중앙에 위치할 레이블 초기화
        la = new JLabel();
        la.setHorizontalAlignment(JLabel.CENTER); // 레이블을 가운데 정렬

        // 레이아웃 설정 및 컴포넌트 추가
        add(topPanel, BorderLayout.NORTH); // 상단에 패널 추가
        add(la, BorderLayout.CENTER); // 중앙에 레이블 추가

        // 마우스 클릭 시 좌표를 저장하고 도형 리스트에 추가하는 리스너 추가
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x = e.getX(); // 클릭한 x 좌표
                y = e.getY(); // 클릭한 y 좌표
                shapes.add(new Shape(selectedShape, x, y)); // 도형 리스트에 추가
                repaint(); // 화면 다시 그리기
            }
        });

        // 프레임을 보이도록 설정
        setVisible(true);
        // 닫기 버튼을 누르면 프로그램 종료
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 그림을 그리는 메서드
    public void paint(Graphics g) {
        super.paint(g); // 기본 페인팅 작업 수행
        g.setColor(Color.BLACK); // 도형의 색상을 빨간색으로 설정
        // 저장된 모든 도형을 그리기
        for (Shape shape : shapes) {
            int shapeX = shape.getX(); // 도형의 x 좌표
            int shapeY = shape.getY(); // 도형의 y 좌표
            switch (shape.getType()) {
                case "Circle":
                    g.drawOval(shapeX - 25, shapeY - 25, 50, 50); // 중심이 클릭한 위치가 되도록 원 그리기
                    break;
                case "Rectangle":
                    g.drawRect(shapeX - 25, shapeY - 25, 50, 50); // 중심이 클릭한 위치가 되도록 사각형 그리기
                    break;
                case "Triangle":
                    int[] xPoints = {shapeX, shapeX - 25, shapeX + 25}; // 삼각형의 x 좌표 배열
                    int[] yPoints = {shapeY - 25, shapeY + 25, shapeY + 25}; // 삼각형의 y 좌표 배열
                    g.drawPolygon(xPoints, yPoints, 3); // 중심이 클릭한 위치가 되도록 삼각형 그리기
                    break;
            }
        }
    }

    // 도형 정보를 저장할 내부 클래스
    class Shape {
        private String type; // 도형의 타입 (Circle, Rectangle, Triangle)
        private int x, y; // 도형의 좌표

        public Shape(String type, int x, int y) {
            this.type = type; // 도형의 타입 설정
            this.x = x; // 도형의 x 좌표 설정
            this.y = y; // 도형의 y 좌표 설정
        }

        public String getType() {
            return type; // 도형의 타입 반환
        }

        public int getX() {
            return x; // 도형의 x 좌표 반환
        }

        public int getY() {
            return y; // 도형의 y 좌표 반환
        }
    }

    // 프로그램의 진입점
    public static void main(String[] args) {
        new DrawingFrame(); // DrawingFrame 인스턴스 생성 및 실행
    }
}
