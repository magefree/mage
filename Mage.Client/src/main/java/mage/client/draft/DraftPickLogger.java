package mage.client.draft;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import mage.view.DraftView;

public class DraftPickLogger {

  private static final Logger LOGGER = Logger.getLogger(DraftPickLogger.class);

  private final Path logPath;
  private final boolean logging;
  private boolean headerWritten = false;

  public DraftPickLogger(File directory, String logFilename) {
    this.logging = true;
    if (!directory.exists()) {
      directory.mkdirs();
    }
    this.logPath = new File(directory, logFilename).toPath();
    try {
      Files.write(logPath, new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException ex) {
      LOGGER.error(null, ex);
    }
  }

  public DraftPickLogger() {
    this.logging = false;
    this.logPath = null;
  }

  public void updateDraft(UUID draftId, DraftView draftView) {
    if (headerWritten) {
      return;
    }
    headerWritten = true;
    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
    StringBuilder buffer = new StringBuilder()
      .append("Event #: ").append(draftId).append("\n")
      .append("Time:    ").append(formatter.format(now)).append('\n');
    buffer.append("Players:\n");
    for (String player : draftView.getPlayers()) {
      buffer.append("    ").append(player).append('\n');
    }
    buffer.append('\n');
    appendToDraftLog(buffer.toString());
  }

  public void logPick(String setCode, int packNo, int pickNo, String pick, String[] currentBooster) {
    StringBuilder b = new StringBuilder();
    if (pickNo == 1) {
      b.append("------ ").append(setCode).append(" ------\n\n");
    }
    b.append("Pack ").append(packNo).append(" pick ").append(pickNo).append(":\n");
    for (String name : currentBooster) {
      b.append(pick.equals(name) ? "--> " : "    ");
      b.append(name);
      b.append('\n');
    }
    b.append('\n');
    appendToDraftLog(b.toString());
  }

  private void appendToDraftLog(String data) {
    if (logging) {
      try {
        Files.write(logPath, data.getBytes(), StandardOpenOption.APPEND);
      } catch (IOException ex) {
        LOGGER.error(null, ex);
      }
    }
  }

}
