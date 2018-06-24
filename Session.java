import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Session {

	Label stopwatchTime;
	static String[] times = new String[3];
	static boolean roundFin = false;

	private final Timer timer = new Timer("Stopwatch");
	private final StopwatchTimerTask task = new StopwatchTimerTask();
	
	public Scene setSceneClock() {
		
		VBox layout = new VBox();
		Button start = new Button("Start");
		Button stop = new Button("Stop");

		Button reset = new Button("Reset");
		Button back = new Button("Back");
		Button saveTime = new Button("Save Time");
		
		start.setOnAction(e -> {
			try {
				timer.scheduleAtFixedRate(task, 0, 1000);
			} catch (IllegalStateException e1) {
				task.resume();
			}
		});
		stop.setOnAction(e -> {
			long timeToSave = task.getSeconds();
			task.pause();
		});

		saveTime.setOnAction(e -> storeTime(task));
		back.setOnAction(e -> WimHofTracker.window.setScene(WimHofTracker.scene));

		reset.setOnAction(e -> task.resetTime());
		
		stopwatchTime = new Label("0:00");
		
		layout.getChildren().addAll(start, stop, stopwatchTime, reset, back, saveTime);
		
		Scene scene = new Scene(layout, 300, 200);
		return scene;
	}

	public static void storeTime(StopwatchTimerTask task){
		if(roundFin){
			AlertBox.display("Day finished", "You already input three rounds for the day." +
					"\nCome back tomorrow and do it again! :)");

		}
		else {
			if(ConfirmBox.display("Confirm", "Are you sure you want to save this time?")){
				boolean filledArray = true;
				for(int i = 0; i < times.length; i++){
					if(times[i] == null){
						times[i] = task.getSeconds() + "";
						filledArray = false;
						System.out.println("Saved: " + times[i]);
						break;
					}
				}

				if(filledArray){
					WimHofTracker.insertData(times);
					roundFin = true;
				}
			}
		}
	}

	public void stop() {
		System.out.println("Stopping session");
		timer.cancel();
	}

	private class StopwatchTimerTask extends TimerTask {

		private boolean paused;
		private long seconds = 0;

		@Override
		public void run() {
			if (!paused) {
				seconds++;
				long minutes = seconds / 60;
				long leftoverSeconds = seconds % 60;
				String timer = minutes + ":" + leftoverSeconds;

				Platform.runLater(() -> {
					stopwatchTime.setText(timer);
				});
			}
		}

		public void pause() {
			paused = true;
		}

		public void resume() {
			paused = false;
		}

		public void resetTime(){
			seconds = 0;
			Platform.runLater(() -> stopwatchTime.setText("0:00"));
		}
		
		public long getSeconds() {
			return seconds;
		}
		
	}
}
