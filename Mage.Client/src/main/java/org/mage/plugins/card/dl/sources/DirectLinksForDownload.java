/**
 * GathererSymbols.java
 *
 * Created on 25.08.2010
 */

package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import mage.client.constants.Constants;
import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;
import static org.mage.plugins.card.utils.CardImageUtils.getImagesDir;

/**
 * Used when we need to point to direct links to download resources from.
 *
 * @author noxx
 */
public class DirectLinksForDownload implements Iterable<DownloadJob> {

    private static final String backsideUrl = "https://upload.wikimedia.org/wikipedia/en/a/aa/Magic_the_gathering-card_back.jpg";

    private static final Map<String, String> directLinks = new LinkedHashMap<>();

    public static final String cardbackFilename = "cardback.jpg";
    public static final String tokenFrameFilename = "tokenFrame.png";

    static {
        directLinks.put(cardbackFilename, backsideUrl);
    }

    public static File outDir;

    public DirectLinksForDownload() {
        outDir = new File(getImagesDir() + Constants.RESOURCE_PATH_DEFAUL_IMAGES);

        if (!outDir.exists()){
            outDir.mkdirs();
        }
    }

    @Override
    public Iterator<DownloadJob> iterator() {
        ArrayList<DownloadJob> jobs = new ArrayList<>();

        for (Map.Entry<String, String> url : directLinks.entrySet()) {
            File dst = new File(outDir, url.getKey());
            jobs.add(new DownloadJob(url.getKey(), fromURL(url.getValue()), toFile(dst)));
        }
        return jobs.iterator();
    }
}
