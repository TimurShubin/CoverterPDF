package com.converter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import com.converter.models.Link;

public class PackageUpdater {

	private final ScheduledExecutorService scheduler;
	private final Map<String, Set<Link>> history = FileConverter.history;

	PackageUpdater() {
		scheduler = Executors.newScheduledThreadPool(1);
		updatePackages();
	}
	
	private void updatePackages() {
		final Runnable updater = new Runnable() {
			public void run() {
				for (Map.Entry<String, Set<Link>> item : history.entrySet()) {
					try {
						FileConverter.displayFiles(item.getKey());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		// если файл обрабатываетс€ дольше 5 минут, то таймер будет неверно обновл€ть данные из каталога
		final ScheduledFuture<?> updaterHandle = scheduler.scheduleAtFixedRate(updater, 0, 60, TimeUnit.SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				updaterHandle.cancel(true);
			}
		}, 60*60, TimeUnit.SECONDS);
		
	}
}
