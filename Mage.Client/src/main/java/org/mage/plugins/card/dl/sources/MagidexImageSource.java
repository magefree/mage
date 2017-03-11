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

import org.mage.plugins.card.images.CardDownloadData;
import java.net.URI;

/**
 *
 * @author Pete Rossi
 */

public class MagidexImageSource implements CardImageSource {
    private static CardImageSource instance = new MagidexImageSource();

    public static CardImageSource getInstance() {
        if (instance == null) {
            instance = new MagidexImageSource();
        }
        return instance;
    }
    @Override
    public String getSourceName() {
        return "magidex.com";
    }

    @Override
    public String getNextHttpImageUrl() {
        return null;
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return null;
    }

    @Override
    public String generateURL(CardDownloadData card) throws Exception {
        String cardDownloadName = card.getDownloadName().toLowerCase();
        String cardSet = card.getSet();

        if (cardDownloadName == null || cardSet == null) {
            throw new Exception("Wrong parameters for image: cardDownloadName: " + cardDownloadName + ",card set: " + cardSet);
        }

        if (card.isSplitCard()) {
            cardDownloadName = cardDownloadName.replaceAll(" // ", "");
        }

        // This will properly escape the url
        URI uri = new URI("http", "magidex.com", "/extstatic/card/" + cardSet + '/' + cardDownloadName + ".jpg", null, null);
        return  uri.toASCIIString();
    }

    @Override
    public String generateTokenUrl(CardDownloadData card) {
        return null;
    }

    @Override
    public float getAverageSize() {
        return 62.0f;
    }

    @Override
    public int getTotalImages() {
        return -1;
    }

    @Override
    public boolean isTokenSource() {
        return false;
    }

    @Override
    public void doPause(String httpImageUrl) {
    }
}
