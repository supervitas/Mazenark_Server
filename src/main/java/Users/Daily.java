package Users;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

public class Daily implements UnityMongoSerializable {
    private String id = "REPORT AS A BUG";
    private String statisticRequired = "REPORT AS A BUG";
    private String description = "REPORT AS A BUG";
    private Integer statisticDelta = 0;
    private Integer statisticWasWhenDailyStarted = 0;
    private Integer statisticNeededToEndDaily = 0;
    private Integer dailyReward = 0;
    private Boolean isFinished = false;

    public Daily() {

    }

    public Daily(String id, String statisticRequired, String description, Integer statisticDelta, Integer statisticWasWhenDailyStarted, Integer dailyReward) {
        this.id = id;
        this.statisticRequired = statisticRequired;
        this.description = description;
        this.statisticDelta = statisticDelta;
        this.statisticWasWhenDailyStarted = statisticWasWhenDailyStarted;
        this.statisticNeededToEndDaily = statisticWasWhenDailyStarted + statisticDelta;
        this.dailyReward = dailyReward;
        this.isFinished = false;
    }

    public void TryAccompishDaily(User owner) {
        if (!isFinished && owner.getStatisticsRecord(statisticRequired) >= statisticNeededToEndDaily) {
            int goldOwnerHas = owner.getStatisticsRecord("Gold");
            owner.setStatisticsRecord("Gold", goldOwnerHas + dailyReward);
            isFinished = true;
        }
    }

    @Override
    public UnityMongoSerializable UpdateFromDocument(Document data) {
        id = data.getString("id");
        statisticRequired = data.getString("statisticRequired");
        description = data.getString("description");
        statisticDelta = data.getInteger("statisticDelta");
        statisticWasWhenDailyStarted = data.getInteger("statisticWasWhenDailyStarted");
        statisticNeededToEndDaily = data.getInteger("statisticNeededToEndDaily");
        dailyReward = data.getInteger("dailyReward");
        isFinished = data.getBoolean("isFinished");
        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        String currentFieldBeingRead = "";
        String tmpID = "";
        String tmpStatisticRequired = "";
        String tmpDescription= "";
        Integer tmpStatisticDelta = 0;
        Integer tmpStatisticWasWhenDailyStarted = 0;
        Integer tmpStatisticNeededToEndDaily = 0;
        Integer tmpDailyReward = 0;
        Boolean tmpIsFinished = false;

        boolean thereWereNoErrors = true;
        try {
            currentFieldBeingRead = "id";
            tmpID = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "statisticRequired";
            tmpStatisticRequired = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "description";
            tmpDescription = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "statisticDelta";
            tmpStatisticDelta = data.getInt(currentFieldBeingRead);

            currentFieldBeingRead = "statisticWasWhenDailyStarted";
            tmpStatisticWasWhenDailyStarted = data.getInt(currentFieldBeingRead);

            currentFieldBeingRead = "statisticNeededToEndDaily";
            tmpStatisticNeededToEndDaily = data.getInt(currentFieldBeingRead);

            currentFieldBeingRead = "dailyReward";
            tmpDailyReward = data.getInt(currentFieldBeingRead);

            currentFieldBeingRead = "isFinished";
            tmpIsFinished = data.getBoolean(currentFieldBeingRead);

        } catch (JSONException e) {
            thereWereNoErrors = false;
            System.out.println("An error has occurred when updating item \"" + id + "\" from JSON when reading \"" + currentFieldBeingRead + "\" field.");
            System.out.println(data.toString());
            e.printStackTrace();
        }

        if (thereWereNoErrors) {
            id = tmpID;
            statisticRequired = tmpStatisticRequired;
            description = tmpDescription;
            statisticDelta = tmpStatisticDelta;
            statisticWasWhenDailyStarted = tmpStatisticWasWhenDailyStarted;
            statisticNeededToEndDaily = tmpStatisticNeededToEndDaily;
            dailyReward = tmpDailyReward;
            isFinished = tmpIsFinished;
        }

        return this;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("id", id);
        result.append("statisticRequired", statisticRequired);
        result.append("description", description);
        result.append("statisticDelta", statisticDelta);
        result.append("statisticWasWhenDailyStarted", statisticWasWhenDailyStarted);
        result.append("statisticNeededToEndDaily", statisticNeededToEndDaily);
        result.append("dailyReward", dailyReward);
        result.append("isFinished", isFinished);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("id", id);
            result.put("statisticRequired", statisticRequired);
            result.put("description", description);
            result.put("statisticDelta", statisticDelta);
            result.put("statisticWasWhenDailyStarted", statisticWasWhenDailyStarted);
            result.put("statisticNeededToEndDaily", statisticNeededToEndDaily);
            result.put("dailyReward", dailyReward);
            result.put("isFinished", isFinished);

        } catch (JSONException e) {
            System.out.println("An error has occurred when converting item \"" + id + "\" to JSON! O_o");
            e.printStackTrace();
        }

        return result;
    }
}
