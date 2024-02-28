package org.mage.plugins.card.dl.sources;

import mage.client.constants.Constants;
import org.mage.plugins.card.dl.DownloadJob;

import java.io.File;
import java.util.*;

import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * TODO: outdated, delete and use xmage tokens instead ?!
 * Used when we need to point to direct links to download resources from.
 *
 * @author noxx
 */
public class DirectLinksForDownload implements Iterable<DownloadJob> {

    private static final Map<String, String> directLinks = new LinkedHashMap<>();

    public static final String cardbackFilename = "cardback.jpg";

    static {
        directLinks.put(cardbackFilename, "https://upload.wikimedia.org/wikipedia/en/a/aa/Magic_the_gathering-card_back.jpg");
    }

    private final File outDir;

    public DirectLinksForDownload() {
        this.outDir = new File(getImagesDir() + Constants.RESOURCE_PATH_DEFAULT_IMAGES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
    }

    @Override
    public Iterator<DownloadJob> iterator() {
        List<DownloadJob> jobs = new ArrayList<>();

        for (Map.Entry<String, String> url : directLinks.entrySet()) {
            File dst = new File(outDir, url.getKey());
            // download images every time (need to update low quality image)
            jobs.add(new DownloadJob(url.getKey(), fromURL(url.getValue()), toFile(dst), true));
        }
        return jobs.iterator();
    }
}
