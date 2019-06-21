package model;

import java.io.File;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class THPolyPlayer {
	
	int count=0;

	Media [] getMusik() {
		File dir = new File("/musik/Kontra K");
		File [] files = dir.listFiles();
		for (File x: files) {
			System.out.println(x.toString());
		}
		Media [] content = new Media [files.length];
		for (int i=0;i<files.length;i++) {
			URL resource = getClass().getResource(files[i].getPath());
			content[i]=new Media(resource.toString());
		}
		return content;
	}
	
	public void startMusik() {
		Media[] media = getMusik();
		final MediaPlayer mediaPlayer = new MediaPlayer(media[0]);
		mediaPlayer.setOnEndOfMedia(() -> {
			count++;
		    new MediaPlayer(media[count]).play();
		});
		mediaPlayer.play();
	}
}
