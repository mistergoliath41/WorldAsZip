package fr.mistergoliath.worldaszip.utils;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;

import fr.mistergoliath.worldaszip.Main;

public class BackupTimer extends Timer {

	private Main main;
	private int timeInSecBase;
	private boolean isStopped;
	
	public BackupTimer(Main main, int timeInSec) {
		this.main = main;
		this.timeInSecBase = timeInSec;
	}
	
	public int getTimeInSecBase() {
		return timeInSecBase;
	}

	public BackupTimer setTimeInSecBase(int timeInSec) {
		this.timeInSecBase = timeInSec;
		return this;
	}

	public BackupTimer start() {
		this.isStopped = false;
		super.schedule(new TimerTask() {
			int timeInSec = timeInSecBase;
			@Override
			public void run() {
				if (isStopped()) cancel();
				if (timeInSec == 0) {
					timeInSec = timeInSecBase;
					ArrayList<String> worldList = (ArrayList<String>)main.getConfig().getStringList("backup.worlds");
					for (String worldName : worldList) {
						getMain().zip(Bukkit.getWorld(worldName));
						System.out.println(worldName);
					}
				}
				//System.out.println("Time: " + timeInSec + "s");
				timeInSec--;
			}
		}, 1000, 1000);
		return this;
	}
	
	public boolean isStopped() {
		return this.isStopped;
	}

	public BackupTimer stop() {
		this.isStopped = true;
		return this;
	}

	public Main getMain() {
		return main;
	}

	public BackupTimer setMain(Main main) {
		this.main = main;
		return this;
	}
	
}
