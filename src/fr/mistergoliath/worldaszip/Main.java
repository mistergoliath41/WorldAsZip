package fr.mistergoliath.worldaszip;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mistergoliath.worldaszip.command.WorldAsZip;
import fr.mistergoliath.worldaszip.utils.BackupTimer;
import fr.mistergoliath.worldaszip.utils.ColoredLogger;
import fr.mistergoliath.worldaszip.utils.Date;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Based on WorldAsZip by tr808axm
 * Base Source: https://github.com/tr808axm/WorldAsZip
 */
public class Main extends JavaPlugin {
	
    private List<String> filesListInDir;
    private ColoredLogger coloredLogger = new ColoredLogger();
    private BackupTimer backupTimer;
    
    @Override
    public void onEnable() {
    	saveDefaultConfig();
    	reloadConfig();
        registerCommands();
        if (!getDataFolder().exists()) getDataFolder().mkdirs();
        int time = this.getConfig().getInt("backup.time");
        if (time == 0) {
        	this.coloredLogger.info("§cThe \"backup.time\" value must be greater than 0 in the \"config.yml\" file.");
        	this.disable();
        	return;
        }
        this.backupTimer = new BackupTimer(this, time).start();
    }

	@Override
    public void onDisable() {
    	backupTimer.stop();
    }

    private void disable() {
    	backupTimer.stop();
		Bukkit.getPluginManager().disablePlugin(this);
	}

    private void registerCommands() {
        getCommand("worldaszip").setExecutor(new WorldAsZip(this));
    }

    public void zip(World world) {
        try {
        	if (!Bukkit.getPluginManager().getPlugin(this.getName()).isEnabled()) {
        		return;
        	}
            File worldDir = world.getWorldFolder();
            String worldName = world.getName();
            File backupDir = new File(getDataFolder() + "/" + worldName);
            if (!backupDir.exists()) backupDir.mkdirs();
            filesListInDir = new ArrayList<String>();
            populateFilesList(worldDir);
            FileOutputStream fos = new FileOutputStream(new File(backupDir.getAbsolutePath(), worldName + "-" + Date.getDateFormatted() +  ".zip"));
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String filePath : filesListInDir) {
                coloredLogger.info("&6Zipping: &e" + filePath);
                ZipEntry ze = new ZipEntry(filePath.substring(worldDir.getAbsolutePath().length() + 1, filePath.length()));
                zos.putNextEntry(ze);
                FileInputStream fis = new FileInputStream(filePath);
                byte[] buffer = new byte[1024];
                int len;
                while((len = fis.read(buffer)) > 0) zos.write(buffer, 0, len);
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
            coloredLogger.info("&aSuccessfully built zip !");
        } catch (IOException e) {
            coloredLogger.info("§cCould't zip world: §e" + e.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    public void populateFilesList(File dir) {
        File[] files = dir.listFiles();
        for(File f : files) {
            if(f.isFile()) filesListInDir.add(f.getAbsolutePath());
            else populateFilesList(f);
        }
    }
}
