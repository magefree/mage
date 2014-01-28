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
package mage.game.draft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mage.cards.Card;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import org.apache.log4j.Logger;

/**
 *
 * @author LevelX2
 */
public abstract class DraftCube {

    public class CardIdentity {
        private String name;
        private String extension;

        public CardIdentity(String name, String extension) {
            this.name = name;
            this.extension = extension;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    private static final Logger logger = Logger.getLogger(DraftCube.class);
    
    private static final Random rnd = new Random();
    private final String name;
    private final int boosterSize = 15;

    protected List<CardIdentity> cubeCards = new ArrayList<CardIdentity>();
    protected List<CardIdentity> leftCubeCards = new ArrayList<CardIdentity>();

    public DraftCube(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<CardIdentity> getCubeCards() {
        return cubeCards;
    }
    
    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<Card>();
        if (leftCubeCards.isEmpty()) {
            leftCubeCards.addAll(cubeCards);
        }
        
        for (int i = 0; i < boosterSize; i++) {
            boolean done = false;
            int notValid = 0;
            while (!done) {
                int index = rnd.nextInt(leftCubeCards.size());
                CardIdentity cardId = leftCubeCards.get(index);
                leftCubeCards.remove(index);
                if (!cardId.getName().isEmpty()) {
                    CardInfo cardInfo = null;
                    if (!cardId.getExtension().isEmpty()) {
                        CardCriteria criteria = new CardCriteria().name(cardId.getName()).setCodes(cardId.extension);
                        List<CardInfo> cardList = CardRepository.instance.findCards(criteria);
                        if (cardList != null && cardList.size() > 0) {
                            cardInfo = cardList.get(0);
                        }
                    } else {
                        cardInfo = CardRepository.instance.findCard(cardId.getName());
                    }
                    
                    if (cardInfo != null) {
                        booster.add(cardInfo.getCard());
                        done = true;
                    } else {
                        logger.warn(new StringBuilder(this.getName()).append(" - Card not found: ").append(cardId.getName()).append(":").append(cardId.extension));
                        notValid++;
                    }
                } else {
                    logger.error(new StringBuilder(this.getName()).append(" - Empty card name: ").append(cardId.getName()).append(":").append(cardId.extension));
                    notValid++;
                }

                if (leftCubeCards.isEmpty()) {
                    leftCubeCards.addAll(cubeCards);
                }
                if (notValid > cubeCards.size()) {
                    logger.error(new StringBuilder(this.getName()).append(" - Booster could not be created, no valid cards found "));
                    done = true;
                }
            }
        }

        return booster;
    }
}
