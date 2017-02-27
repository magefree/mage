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
package mage.cards.repository;

import java.util.ArrayList;
import java.util.List;
import mage.cards.*;
import org.apache.log4j.Logger;

/**
 *
 * @author North
 */
public final class CardScanner {

    public static boolean scanned = false;

    private static final Logger logger = Logger.getLogger(CardScanner.class);

    public static void scan() {
        if (scanned) {
            return;
        }
        scanned = true;

        List<CardInfo> cardsToAdd = new ArrayList<>();

        for (ExpansionSet set : Sets.getInstance().values()) {
            ExpansionInfo expansionInfo = ExpansionRepository.instance.getSetByCode(set.getCode());
            if (expansionInfo == null) {
                ExpansionRepository.instance.add(new ExpansionInfo(set));
            } else if (!expansionInfo.name.equals(set.getName())
                    || !expansionInfo.code.equals(set.getCode())
                    || (expansionInfo.blockName == null ? set.getBlockName() != null : !expansionInfo.blockName.equals(set.getBlockName()))
                    || !expansionInfo.releaseDate.equals(set.getReleaseDate())
                    || !expansionInfo.type.equals(set.getSetType())
                    || expansionInfo.boosters != set.hasBoosters()
                    || expansionInfo.basicLands != set.hasBasicLands()) {
                ExpansionRepository.instance.update(expansionInfo);
            }
        }
        ExpansionRepository.instance.setContentVersion(ExpansionRepository.instance.getContentVersionConstant());

        for (ExpansionSet set : Sets.getInstance().values()) {
            for (ExpansionSet.SetCardInfo setInfo : set.getSetCardInfo()) {
                if (CardRepository.instance.findCard(set.getCode(), setInfo.getCardNumber()) == null) {
                    Card card = CardImpl.createCard(setInfo.getCardClass(),
                            new CardSetInfo(setInfo.getName(), set.getCode(), setInfo.getCardNumber(),
                                    setInfo.getRarity(), setInfo.getGraphicInfo()));
                    if (card != null) {
                        cardsToAdd.add(new CardInfo(card));
                        if (card instanceof SplitCard) {
                            SplitCard splitCard = (SplitCard) card;
                            cardsToAdd.add(new CardInfo(splitCard.getLeftHalfCard()));
                            cardsToAdd.add(new CardInfo(splitCard.getRightHalfCard()));
                        }
                    }
                }
            }
        }

        if (!cardsToAdd.isEmpty()) {
            logger.info("Cards need storing in DB: " + cardsToAdd.size());
            CardRepository.instance.addCards(cardsToAdd);
        }
        CardRepository.instance.setContentVersion(CardRepository.instance.getContentVersionConstant());
    }
}
