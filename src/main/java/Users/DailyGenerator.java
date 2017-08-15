package Users;

import java.util.ArrayList;
import java.util.Random;

public class DailyGenerator {
    public static ArrayList<Daily> GetRandomDailies(int count, User owner) {
        final ArrayList<Daily> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            int randomNumber = new Random().nextInt() % 11;
            switch (randomNumber) {
                case 0:
                    result.add(new Daily("Kill monsters", "Kills", "Kill 5 monsters today.", 3, owner.getStatisticsRecord("Kills"), 100));
                    break;
                case 1:
                    result.add(new Daily("Kill monsters", "Kills", "Kill 20 monsters today. Prove yourself brave.", 10, owner.getStatisticsRecord("Kills"), 500));
                    break;
                case 2:
                    result.add(new Daily("Use teleport", "Teleport usages", "Explore unknown regions.", 1, owner.getStatisticsRecord("Teleport usages"), 50));
                    break;
                case 3:
                    result.add(new Daily("Escape the maze", "Wins", "Travel to another side of the planet.", 1, owner.getStatisticsRecord("Wins"), 100));
                    break;
                case 4:
                    result.add(new Daily("Escape the maze", "Wins", "Fulfil your trade obligations.", 4, owner.getStatisticsRecord("Wins"), 500));
                    break;
                case 5:
                    result.add(new Daily("Pick up an item", "Items picked up", "Find something not belonging to your world.", 1, owner.getStatisticsRecord("Items picked up"), 50));
                    break;
                case 6:
                    result.add(new Daily("Earn 10'000 gold!", "Gold", "Stash ludicrous amount of money!", 10000, 0, 5000));
                    break;
                case 7:
                    result.add(new Daily("Kill another player", "Player kills", "No one will know...", 1, owner.getStatisticsRecord("Player kills"), 1000));
                    break;
                case 8:
                    result.add(new Daily("Discover biomes", "Biomes walked", "Discover five different biomes in one go.", 5, 0, 200));
                    break;
                case 9:
                    result.add(new Daily("Kill monsters", "Kills in this game", "Kill 3 monsters in one travel.", 3, 0, 200));
                    break;
                case 10:
                    result.add(new Daily("Defeat a boss", "Bosses killed", "Find and defeat big scary boss monster.", 1, owner.getStatisticsRecord("Bosses killed"), 500));
                    break;
            }
        }

        return result;
    }
}
