package test;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ToiletExample {
    public static void main(String[] args) {
        try {
            // 명령어: toilet --font=<폰트명> "안녕하세요"
            ProcessBuilder processBuilder = new ProcessBuilder("toilet", "--font=big", "안녕하세요");
            Process process = processBuilder.start();

            // toilet 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 프로세스 종료 코드 확인
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Toilet 명령 실행 중 오류 발생! (exit code: " + exitCode + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
