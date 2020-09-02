import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class RoomRepository {
    private List<Room> rooms;
    private final Path path;
    private RoomType RoomType;

    public RoomRepository(String filename) {
        path = Paths.get(filename);
        try {
            rooms = Files.lines(path)
                    .map(this::createRoom)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Data reader error");
        }
    }

    public List<Room> getAll() {
        return rooms;
    }

    public String get(int id) {
        return createFileLine(Objects.requireNonNull(rooms.stream()
                .filter(room -> room.getId() == id)
                .findFirst()
                .orElse(null)));
    }

    private Room createRoom(String fileLine) {
        String[] lineParts = fileLine.split(",");
        int id = Integer.parseInt(lineParts[0]);
        int sengePlass = Integer.parseInt(lineParts[1]);
        double pris = Double.parseDouble(lineParts[2]);
        String room = lineParts[3];
        ArrayList<RoomType> roomTypes = new ArrayList<>(Arrays.asList(RoomType.values()));
        RoomType roomType = null;
        for (RoomType roomType1 : roomTypes) {
            if (room.equals(roomType)){
                roomType=roomType1;
            }
        }
        return new Room(sengePlass, pris, roomType);
    }

    private int generateNextId() {
        return rooms.stream()
                .mapToInt(Room::getId)
                .max()
                .orElse(0) + 1;
    }

    private String createFileLine(Room room) {
        return room.getId() + "," + room.getSengePlass() + "," + room.getPris() + "," + room.getRoomType();
    }
    public void createNextLine(String string) throws IOException{
        List<String> fileLines = Arrays.asList(",",string);
       Files.write(path, fileLines, StandardOpenOption.APPEND);
    }

    private void save() {
        List<String> fileLines = rooms.stream()
                .map(this::createFileLine)
                .collect(Collectors.toList());
        try {
            Files.write(path, fileLines);
        } catch (IOException e) {
            throw new RuntimeException("Data save error");
        }
    }

    public void add(Room room) {
        room.setId(generateNextId());
        rooms.add(room);
        save();
    }

    public void delete(int id) {
        rooms.removeIf(room -> room.getId() == id);
        save();
    }
}
