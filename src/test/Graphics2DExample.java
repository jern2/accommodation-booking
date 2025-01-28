package test;
import javax.swing.*;
import java.awt.*;

public class Graphics2DExample extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Graphics2D 객체로 변환
        Graphics2D g2d = (Graphics2D) g;

        // 폰트 설정 (한글 지원 폰트 사용)
        Font font = new Font("Nanum Gothic", Font.BOLD, 48); // 한글 지원 폰트
        g2d.setFont(font);

        // 컬러 설정
        g2d.setColor(Color.BLUE);

        // 텍스트 그리기
        String text = "안녕하세요, Graphics2D!";
        g2d.drawString(text, 50, 100); // x: 50, y: 100 좌표에 텍스트 출력

        // 다른 스타일의 텍스트 추가
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Malgun Gothic", Font.ITALIC, 36)); // 다른 폰트와 스타일
        g2d.drawString("한글 출력 예제입니다.", 50, 150);
    }

    public static void main(String[] args) {
        // JFrame 설정
        JFrame frame = new JFrame("Graphics2D 한글 출력 예제");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // JPanel 추가
        Graphics2DExample panel = new Graphics2DExample();
        frame.add(panel);

        // 창 표시
        frame.setVisible(true);
    }
}
