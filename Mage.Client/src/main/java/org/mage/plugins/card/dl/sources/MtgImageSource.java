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

import java.util.HashMap;
import java.util.Map;
import static javax.swing.UIManager.put;
import mage.client.dialog.PreferencesDialog;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.utils.CardImageUtils;

/**
 *
 * @author LevelX2
 */

public class MtgImageSource implements CardImageSource {

    private static CardImageSource instance = new MtgImageSource();
    
    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new MtgImageSource();
        }
        return instance;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        Integer collectorId = card.getCollectorId();
        String cardSet = card.getSet();
        if (collectorId == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: collector id: " + collectorId + ",card set: " + cardSet);
        }
        String set = CardImageUtils.updateSet(cardSet, true);
               
        StringBuilder url = new StringBuilder("http://mtgimage.com/set/");
        url.append(set.toUpperCase()).append("/").append(card.getName());

        if (card.isTwoFacedCard()) {
            url.append(card.isSecondSide() ? "b" : "a");
        }
        if (card.isSplitCard()) {
            url.append("a");
        }
        if (card.isFlipCard()) {
            if (card.isFlippedSide()) { // download rotated by 180 degree image
                url.append("b");
            } else {
                url.append("a");
            }
        }
        url.append(".jpg");

        return url.toString();
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public Float getAverageSize() {
        return 70.0f;
    }
}