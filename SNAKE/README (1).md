# Dự Án: Snake Game Java (MVC Pattern)
## 1. Giới thiệu dự án
Trò chơi **Snake Game** (Rắn săn mồi) là sản phẩm bài tập lớn cuối môn, được xây dựng trên ngôn ngữ lập trình Java. Dự án tập trung vào việc áp dụng mô hình thiết kế **MVC (Model-View-Controller)** để quản lý mã nguồn tối ưu và sử dụng các kỹ thuật lập trình hướng đối tượng (OOP) nâng cao.
### Thành viên thực hiện:
 * **Văn Thức**
 * **Minh Thành**
 * **Hoàng Nam**
## 2. Các tính năng nổi bật
 * **Lối chơi Classic nâng cấp**: Rắn di chuyển mượt mà, hỗ trợ ăn mồi thường và mồi Bonus lấp lánh.
 * **Thử thách định kỳ (Blue Bomb)**: Cứ mỗi **15 giây**, một quả bom xanh sẽ xuất hiện bất ngờ. Nếu va chạm, người chơi bị trừ 2 điểm và rắn bị cắt bớt 2 đốt thân.
 * **Hệ thống Level & Tốc độ**: Độ khó tăng dần theo điểm số; tốc độ rắn nhanh hơn và vật cản (Obstacles) xuất hiện dày đặc hơn khi lên cấp.
 * **Giao diện tùy biến (Settings)**:
   * Chế độ Sáng/Tối (Light/Dark Mode).
   * Bật/Tắt âm thanh nhạc nền và hiệu ứng.
   * Chế độ rung (Vibrate) khi gặp sự cố hoặc ăn mồi lớn.
 * **Lưu trữ dữ liệu**: Tự động lưu và hiển thị Điểm cao (High Score) kỷ lục.
## 3. Cấu trúc mã nguồn (MVC)
Dự án được chia thành các package rõ ràng để dễ dàng quản lý và mở rộng:
 * **com.snakegame.model**: Chứa các thực thể dữ liệu như Snake, Food, Bomb, Obstacle. Các lớp này kế thừa từ lớp trừu tượng GameObject.
 * **com.snakegame.view**: Chứa lớp GamePanel chịu trách nhiệm vẽ đồ họa bằng Graphics2D và hiển thị giao diện người dùng (UI).
 * **com.snakegame.controller**: Gồm GameController xử lý logic chính và InputHandler tiếp nhận tương tác phím/chuột.
 * **com.snakegame.utils**: Các công cụ hỗ trợ như SoundManager, FileManager, hằng số Constants và Custom Exception để xử lý lỗi hệ thống.
## 4. Hướng dẫn cài đặt và khởi chạy
### Yêu cầu hệ thống:
 * Java Development Kit (JDK) 17 trở lên.
 * Một IDE bất kỳ (VS Code, IntelliJ IDEA, hoặc Eclipse).
### Các bước thực hiện:
 1. **Tải mã nguồn**: Clone dự án từ GitHub hoặc tải file ZIP về máy.
 2. **Mở dự án**: Mở thư mục dự án bằng IDE của bạn.
 3. **Cấu hình thư viện**: Đảm bảo các file âm thanh và hình ảnh trong thư mục assets/ đã được nhận diện đúng đường dẫn.
 4. **Chạy ứng dụng**: Tìm đến file Main.java  và nhấn **Run**.
## 5. Nhật ký phát triển (Commit History)
Nhóm tuân thủ quy trình chia task theo commit trên GitHub:
 * **Commit 1**: Khởi tạo cấu trúc thư mục MVC và các lớp Model cơ bản.
 * **Commit 2**: Xây dựng bộ máy Render đồ họa và Menu chính.
 * **Commit 3**: Xử lý logic di chuyển, va chạm và ăn mồi.
 * **Commit 4**: Tích hợp chu kỳ 15 giây cho Bom và hệ thống tính điểm.
 * **Commit 5**: Hoàn thiện âm thanh, hiệu ứng rung và bảng cài đặt.
*Dự án này được cam kết thực hiện bởi toàn bộ thành viên trong nhóm, mọi mã nguồn đều được quản lý qua hệ thống Git.*