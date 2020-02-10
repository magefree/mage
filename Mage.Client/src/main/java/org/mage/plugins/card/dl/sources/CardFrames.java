
package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mage.plugins.card.dl.DownloadJob;
import static org.mage.plugins.card.dl.DownloadJob.fromURL;
import static org.mage.plugins.card.dl.DownloadJob.toFile;

/**
 *
 * @author LevelX2
 */
    
public class CardFrames implements Iterable<DownloadJob> {    
    
    private static final String FRAMES_PATH = File.separator + "frames";
    private static final File DEFAULT_OUT_DIR = new File("plugins" + File.separator + "images" + FRAMES_PATH);
    private static File outDir = DEFAULT_OUT_DIR;
    
    static final String  BASE_DOWNLOAD_URL = "http://ct-magefree.rhcloud.com/resources/img/";
    static final String  TEXTURES_FOLDER = "textures";
    static final String  PT_BOXES_FOLDER = "pt";
    
    private static final String[] TEXTURES =  {"U", "R", "G", "B", "W", "A",
                                               "BG_LAND", "BR_LAND", "WU_LAND", "WB_LAND", "UB_LAND", "GW_LAND", "RW_LAND", 
                                               "RG_LAND", "GU_LAND", "UR_LAND"
                                               // NOT => "BW_LAND","BU_LAND","WG_LAND","WR_LAND",
    };
    private static final String[] PT_BOXES =  {"U", "R", "G", "B", "W", "A"};
    
    public CardFrames(String path) {
        if (path == null) {
            useDefaultDir();
        } else {
            changeOutDir(path);
        }
    }
    
    @Override
    public Iterator<DownloadJob> iterator() {
        List<DownloadJob> jobs = new ArrayList<>();
        for (String texture : TEXTURES) {
            jobs.add(generateDownloadJob(TEXTURES_FOLDER, texture));
        }
        for (String pt_box : PT_BOXES) {
            jobs.add(generateDownloadJob(PT_BOXES_FOLDER, pt_box));
        }
        return jobs.iterator();
    }

    private DownloadJob generateDownloadJob(String dirName, String name) {
        File dst = new File(outDir, name + ".png");
        String url = BASE_DOWNLOAD_URL + dirName + '/' + name + ".png";
        return new DownloadJob("frames-" + dirName + '-' + name, fromURL(url), toFile(dst));
    }    
    
    private void useDefaultDir() {
        outDir = DEFAULT_OUT_DIR;
    }    
    
    private void changeOutDir(String path) {
        File file = new File(path + FRAMES_PATH);
        if (file.exists()) {
            outDir = file;
        } else {
            file.mkdirs();
            if (file.exists()) {
                outDir = file;
            }
        }
    }    
}
