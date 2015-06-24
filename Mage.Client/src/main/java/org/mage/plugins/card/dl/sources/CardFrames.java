/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.plugins.card.dl.sources;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
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
        ArrayList<DownloadJob> jobs = new ArrayList<>();
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
        String url = BASE_DOWNLOAD_URL + dirName + "/" + name + ".png";
        return new DownloadJob("frames-" + dirName + "-" + name, fromURL(url), toFile(dst));
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
