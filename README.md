# # SNAKE GAME - NHÓM VĂN THỨC, MINH THÀNH, HOÀNG NAM

## 📝 1. Giới thiệu dự án
Trò chơi *Snake Game* (Rắn săn mồi) là sản phẩm bài tập lớn môn Lập trình Java. Dự án được phát triển dựa trên ngôn ngữ Java, sử dụng thư viện *Java Swing* cho giao diện và áp dụng chặt chẽ mô hình thiết kế *MVC (Model-View-Controller)* để đảm bảo mã nguồn dễ quản lý, mở rộng và bảo trì.

### 👥 Thành viên thực hiện:
* *Văn Thức*
* *Minh Thành*
* *Hoàng Nam*

---

## ✨ 2. Các tính năng nổi bật
* *🎮 Lối chơi Classic & Hiện đại:* Di chuyển rắn ăn mồi để tăng điểm số và chiều dài.
* *💣 Thử thách Bom Xanh (15 giây):* Cứ mỗi *15 giây*, một quả bom sẽ xuất hiện ngẫu nhiên. Người chơi có 5 giây để né tránh. Nếu va chạm, bạn sẽ bị trừ 2 điểm và rắn bị cắt ngắn 2 đốt thân.
**📈 Hệ thống Level:** Độ khó tăng dần theo điểm số. Càng lên cao, tốc độ di chuyển của rắn càng nhanh và vật cản xuất hiện càng nhiều.
**🍎 Bonus Food:** Mồi đặc biệt xuất hiện ngẫu nhiên giúp cộng nhiều điểm hơn mồi thường.
**🏆 High Score:** Tự động lưu và hiển thị điểm cao nhất từ file dữ liệu highscore_classic.txt.
**🔊 Âm thanh & Hiệu ứng:** Nhạc nền lôi cuốn, hiệu ứng âm thanh sống động và tính năng rung (Vibrate) khi ăn mồi đặc biệt.

---

## 🕹️ 3. Cách chơi
Người chơi điều khiển rắn di chuyển linh hoạt trên bản đồ bằng hai bộ phím:
* *Cách 1:* Sử dụng các phím mũi tên (*Up, Down, Left, Right*).
* *Cách 2:* Sử dụng cụm phím chữ (*W, A, S, D*).
* *Mục tiêu:* Ăn thật nhiều mồi, tránh đâm vào tường, vật cản và đặc biệt là né tránh các quả Bom xuất hiện định kỳ.

---

## 🚀 4. Hướng dẫn cài đặt (QUAN TRỌNG)

Để đảm bảo trò chơi vận hành mượt mà và *không bị lỗi mất âm thanh* (lỗi đường dẫn assets/), vui lòng thực hiện đúng theo các bước sau:

### Yêu cầu hệ thống:
* *Java SDK:* Cài đặt JDK 17 trở lên (Khuyến nghị JDK 21 hoặc 25).
* *IDE:* Visual Studio Code (đã cài Java Extension Pack).

### Các bước khởi chạy:
1. *Tải mã nguồn:* Clone hoặc tải file ZIP dự án từ GitHub về máy.
2. *Mở dự án (BƯỚC CỐT LÕI):* * Mở VS Code.
   * Chọn *File -> Open Folder*.
   * **Trỏ trực tiếp vào thư mục tên là SNAKE** (Thư mục chứa trực tiếp các file README.md, thư mục src và thư mục assets).
   * *Chú ý: Nếu bạn mở thư mục cha bên ngoài, Java sẽ không tìm thấy thư mục assets, dẫn đến lỗi không phát được nhạc nền và hiệu ứng.*
3. *Chạy Game:* * Mở file src/com/snakegame/Main.java.
   * Nhấn nút *Run* (biểu tượng mũi tên xanh) trên VS Code để bắt đầu chơi.

---

## 📂 5. Cấu trúc thư mục
* src/com/snakegame/model: Chứa dữ liệu (Snake, Food, Bomb, Obstacle).
* src/com/snakegame/view: Chứa giao diện hiển thị (GamePanel, GameFrame).
* src/com/snakegame/controller: Chứa logic điều khiển (GameController, InputHandler).
* src/com/snakegame/utils: Chứa công cụ hỗ trợ (Âm thanh, Lưu file, Ngoại lệ).
* assets/: Chứa các file nhạc .wav và hình ảnh nền.

---
Dự án được thực hiện bởi nhóm Văn Thức - Minh Thành - Hoàng Nam.
