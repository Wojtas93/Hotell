import java.util.List;

public class Room{
    private int id;
    private int sengePlass;
    private double pris;
    private RoomType roomType;
    private List<Room> roomList;

    public Room(int sengePlass, double pris, RoomType roomType) {
        this.id = generateNextId();
        this.sengePlass = sengePlass;
        this.pris = pris;
        this.roomType = roomType;
    }

    private int generateNextId() {
        return roomList.stream()
                .mapToInt(Room::getId)
                .max()
                .orElse(0) + 1;
    }

    public Integer getId() {
        return id;
    }


    public int getSengePlass() {
        return sengePlass;
    }

    public double getPris() {
        return pris;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setId(int id) {
        this.id = id;
    }
}
