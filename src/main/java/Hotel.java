import java.io.IOException;
import java.util.List;


public class Hotel {
    RoomRepository roomRepository = new RoomRepository("src\\roomRepository.txt");

    public void bookRoom(int roomId, int personer, String date) throws IOException {
        String room = roomRepository.get(roomId);
        roomRepository.createNextLine(Integer.toString(personer));
        roomRepository.createNextLine(date);
    }
    public Double totalPris(int roomId){
        String[] lineParts = roomRepository.get(roomId).split(",");
      return Double.parseDouble(lineParts[2]);
      
    }
}
