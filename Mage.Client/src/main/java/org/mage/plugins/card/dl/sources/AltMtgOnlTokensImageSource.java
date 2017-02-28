/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.plugins.card.dl.sources;

import org.apache.log4j.Logger;
import org.mage.plugins.card.images.CardDownloadData;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author spjspj
 */
public class AltMtgOnlTokensImageSource implements CardImageSource {

    private static final Logger logger = Logger.getLogger(AltMtgOnlTokensImageSource.class);
    private static CardImageSource instance = new AltMtgOnlTokensImageSource();
    private static int maxTimes = 0;

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new AltMtgOnlTokensImageSource();
        }
        return instance;
    }

    @Override
    public String getSourceName() {
        return "http://alternative.mtg.onl/tokens/";
    }

    @Override
    public Float getAverageSize() {
        return 26.7f;
    }

    @Override
    public String getNextHttpImageUrl() {
        if (copyUrlToImage == null) {
            setupLinks();
        }

        for (String key : copyUrlToImageDone.keySet()) {
            if (copyUrlToImageDone.get(key) < maxTimes) {
                copyUrlToImageDone.put(key, maxTimes);
                return key;
            }
        }
        if (maxTimes < 2) {
            maxTimes++;
        }
        for (String key : copyUrlToImageDone.keySet()) {
            if (copyUrlToImageDone.get(key) < maxTimes) {
                copyUrlToImageDone.put(key, maxTimes);
                return key;
            }
        }
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        if (httpImageUrl != null) {
            return copyUrlToImage.get(httpImageUrl);
        }
        return null;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        return null;
    }

    HashMap<String, String> copyUrlToImage = null;
    HashMap<String, String> copyImageToUrl = null;
    HashMap<String, Integer> copyUrlToImageDone = null;

    private void setupLinks() {
        if (copyUrlToImage != null) {
            return;
        }
        copyUrlToImage = new HashMap<>();
        copyImageToUrl = new HashMap<>();
        copyUrlToImageDone = new HashMap<>();

        copyUrlToImage.put("SCG_CC_002-Penguin.jpg", "BIRD.WU.BIRD.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_CC_005-Vampire.jpg", "VAMPIRE.B.VAMPIRE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_CC_006-Panda.jpg", "BEAR.G.BEAR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_CC_008-Force-of-Squirrel.jpg", "SQUIRREL.G.SQUIRREL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_CC_013-Zombie.jpg", "ZOMBIE.B.ZOMBIE.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_GN_003-Ooze.jpg", "OOZE.G.OOZE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_GN_005-Mammoth.jpg", "ELEPHANT.G.ELEPHANT.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("SCG_GN_006-Wurm.jpg", "WURM.G.WURM.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("SCG_GN_007-Rat.jpg", "RAT.B.RAT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_GN_010-Wolf.jpg", "WOLF.G.WOLF.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_GN_011-Bee.jpg", "INSECT.G.INSECT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_GN_012-Bunny.jpg", "RABBIT.G.RABBIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_GN_013-Piglet.jpg", "BOAR.G.BOAR.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_GN_014-Monkey.jpg", "APE.G.APE.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("SCG_GN_015-Kraken.jpg", "KRAKEN.U.KRAKEN.CREATURE.9.9.full.jpg");
        copyUrlToImage.put("SCG_GN_016-Hippo.jpg", "HIPPO.G.HIPPO.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_GN_019-Reindeer.jpg", "CARIBOU.W.CARIBOU.CREATURE.0.1.full.jpg");
        copyUrlToImage.put("SCG_GN_020-Grizzlybrand.jpg", "DEMON.B.DEMON.CREATURE.5.5.full.jpg");
        copyUrlToImage.put("SCG_IP_002-Elemental.jpg", "ELEMENTAL.BR.ELEMENTAL.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_IP_003-Soldier.jpg", "SOLDIER.W.SOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_IP_005-Spirit.jpg ", "SPIRIT.W.SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_IP_010-Golem.jpg", "GOLEM..GOLEM.ARTIFACTCREATURE.3.3.full.jpg");
        copyUrlToImage.put("SCG_IP_011-Pegasus.jpg", "PEGASUS.W.PEGASUS.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_IP_012-Soldier.jpg", "SOLDIER.WR.SOLDIER.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_IP_016-Rhino.jpg", "RHINO.G.RHINO.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("SCG_IP_017-Spider.jpg", "SPIDER.B.SPIDER.CREATURE.1.2.full.jpg");
        copyUrlToImage.put("SCG_PO_003-Spirit.jpg", "SPIRIT..SPIRIT.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_PO_007-Dragon.jpg", "DRAGON.R.DRAGON.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_PO_009-Faerie.jpg", "FAERIE.U.FAERIE.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_PO_010-Goblin.jpg", "GOBLIN.R.GOBLIN.CREATURE.1.1.full.jpg");
        copyUrlToImage.put("SCG_P_001-Angel.jpg", "ANGEL.W.ANGEL.CREATURE.4.4.full.jpg");
        copyUrlToImage.put("SCG_P_002-Penguin.jpg", "BIRD.W.BIRD.CREATURE.3.4.full.jpg");
        copyUrlToImage.put("SCG_RC_001-Kitten.jpg", "CAT.B.CAT.CREATURE.2.1.full.jpg");
        copyUrlToImage.put("SCG_RC_002-Penguin.jpg", "BIRD.U.BIRD.CREATURE.2.2.full.jpg");
        copyUrlToImage.put("SCG_RC_009-Aia,-Ascended.jpg", "AVATAR.W.AVATAR.CREATURE.S.S.full.jpg");
        copyUrlToImage.put("SCG_SC_001-Cyclops.jpg", "BEAST.G.BEAST.CREATURE.3.3.full.jpg");
        copyUrlToImage.put("SCG_SC_002-Soldier.jpg", "SOLDIER.R.SOLDIER.CREATURE.1.1.full.jpg");


        for (String key : copyUrlToImage.keySet()) {
            copyUrlToImageDone.put(key, maxTimes);
            copyImageToUrl.put(copyUrlToImage.get(key), key);
        }
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) throws IOException {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        return null;
    }

    @Override
    public Integer getTotalImages() {
        if (copyUrlToImage == null) {
            setupLinks();
        }
        if (copyUrlToImage != null) {
            return copyImageToUrl.size();
        }
        return -1;
    }
    
    @Override
    public Boolean isTokenSource() {
        return true;
    }
    
    @Override
    public void doPause(String httpImageUrl) {
    }
}
