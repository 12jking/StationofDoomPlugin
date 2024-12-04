package de.j.deathMinigames.main;
import de.j.deathMinigames.database.PlayerDataDatabase;
import de.j.stationofdoom.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class PlayerData {
    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized UUID getUUID() {
        return uuid;
    }

    public synchronized void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public synchronized Player getPlayer() {
        return player;
    }

    public synchronized PlayerMinigameStatus getStatus() {
        return status;
    }

    public synchronized void setStatus(PlayerMinigameStatus status) {
        this.status = status;
    }

    public synchronized Inventory getLastDeathInventory() {
        if(lastDeathInventory == null) {
            Main.getMainLogger().warning("Tried accessing lastDeathInventory of " + this.name + " but inventory is not set!");
            return Bukkit.createInventory(null, 9*6);
        }
        return lastDeathInventory;
    }

    public synchronized void setLastDeathInventory(Inventory lastDeathInventory) {
        this.lastDeathInventory = lastDeathInventory;
    }

    public synchronized Location getLastDeathLocation() {
        if(lastDeathLocation == null) {
            Main.getMainLogger().warning("Tried accessing lastDeathLocation of " + this.name + " but location is not set!");
            return new Location(Bukkit.getWorld("world"), 0, 0, 0);
        }
        return lastDeathLocation;
    }

    public synchronized void setLastDeathLocation(Location lastDeathLocation) {
        this.lastDeathLocation = lastDeathLocation;
    }

    public synchronized int getDecisionTimer() {
        return decisionTimer;
    }

    public synchronized void setDecisionTimer(int decisionTimer) {
        this.decisionTimer = decisionTimer;
    }

    public boolean getHasWonParkourAtleastOnce() {
        return hasWonParkourAtleastOnce;
    }

    public void setHasWonParkourAtleastOnce(boolean hasWonParkourAtleastOnce) {
        this.hasWonParkourAtleastOnce = hasWonParkourAtleastOnce;
    }

    public synchronized int getBestParkourTime() {
        if(bestParkourTime == -1 && !getHasWonParkourAtleastOnce()) {
            Main.getMainLogger().warning("Tried getting bestParkourTime of " + this.name + " but player has not won once!");
        }
        return bestParkourTime;
    }

    public synchronized void setBestParkourTime(int bestParkourTime) {
        this.bestParkourTime = bestParkourTime;
    }

    public synchronized int getDifficulty() {
        return difficulty;
    }

    public synchronized void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public synchronized boolean getIntroduction() {
        return introduction;
    }

    public synchronized void setIntroduction(boolean introduced) {
        this.introduction = introduced;
    }

    public synchronized boolean getUsesPlugin() {
        return usesPlugin;
    }

    public synchronized void setUsesPlugin(boolean usesPlugin) {
        this.usesPlugin = usesPlugin;
    }

    private volatile String name; // in database
    private volatile UUID uuid; // in database
    private final Player player;
    private volatile PlayerMinigameStatus status; // in database
    private volatile Inventory lastDeathInventory;
    private volatile Location lastDeathLocation;
    private volatile boolean introduction; // in database
    private volatile boolean usesPlugin; // in database
    private volatile int difficulty; // in database
    private volatile int decisionTimer; // in database
    private volatile boolean hasWonParkourAtleastOnce; // in database
    private volatile int bestParkourTime; // in database

    public PlayerData(Player player) {
        Config config = Config.getInstance();
        this.name = player.getName();
        this.uuid = player.getUniqueId();
        this.player = player;
        this.status = PlayerMinigameStatus.alive;
        this.lastDeathInventory = Bukkit.createInventory(null, 9*6);
        this.decisionTimer = config.checkTimeToDecideWhenRespawning();
        this.hasWonParkourAtleastOnce = false;
        this.bestParkourTime = -1;
        this.difficulty = 0;
        this.introduction = false;
        this.usesPlugin = true;
        Main.getPlugin().getLogger().info("in constructor " + this.usesPlugin);
    }
    public PlayerData(String name, String uuid, boolean introduction, boolean usesPlugin, int difficulty, int decisionTimer, boolean hasWonParkourAtleastOnce, int bestParkourTime) {
        Config config = Config.getInstance();
        this.name = name;
        this.uuid = UUID.fromString(uuid);
        this.player = Bukkit.getPlayer(uuid);
        this.status = PlayerMinigameStatus.alive;
        this.lastDeathInventory = Bukkit.createInventory(null, 9*6);
        this.decisionTimer = config.checkTimeToDecideWhenRespawning();
        this.hasWonParkourAtleastOnce = hasWonParkourAtleastOnce;
        this.bestParkourTime = bestParkourTime;
        this.difficulty = difficulty;
        this.introduction = introduction;
        this.usesPlugin = usesPlugin;
        Main.getPlugin().getLogger().info("in constructor " + this.usesPlugin);
    }

    public void updateName() {
        this.name = player.getName();
    }

    public void setDecisionTimerDefault() {
        Config config = Config.getInstance();
        this.decisionTimer = config.checkTimeToDecideWhenRespawning();
    }

    public void setLocationAndInventoryNull() {
        this.lastDeathLocation = null;
        this.lastDeathInventory = null;
    }

    public void resetDecisionTimerAndStatus(){
        this.status = PlayerMinigameStatus.alive;
        setDecisionTimerDefault();
    }
}