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

package mage.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {

    protected static Random rnd = new Random();

    protected String name;
    protected String code;
    protected Date releaseDate;
    protected ExpansionSet parentSet;
    protected SetType setType;
    protected boolean hasBasicLands = true;

    protected String blockName;
    protected boolean hasBoosters = false;
    protected int numBoosterSpecial;
    protected int numBoosterLands;
    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced;
    protected int ratioBoosterMythic;

    protected String packageName;

    public ExpansionSet(String name, String code, String packageName, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public ExpansionSet getParentSet() {
        return parentSet;
    }

    public SetType getSetType() {
        return setType;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getBlockName() {
        return blockName;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<>();
        if (!hasBoosters) {
            return booster;
        }

        List<CardInfo> common = getCommon();

        CardCriteria criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.UNCOMMON).doubleFaced(false);
        List<CardInfo> uncommon = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.RARE).doubleFaced(false);
        List<CardInfo> rare = CardRepository.instance.findCards(criteria);

        criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.MYTHIC).doubleFaced(false);
        List<CardInfo> mythic = CardRepository.instance.findCards(criteria);
     
        if (numBoosterLands > 0) {
            criteria = new CardCriteria();
            criteria.setCodes(!hasBasicLands && parentSet != null ? parentSet.code : this.code).rarities(Rarity.LAND).doubleFaced(false);
            List<CardInfo> basicLand = CardRepository.instance.findCards(criteria);
            for (int i = 0; i < numBoosterLands; i++) {
                addToBooster(booster, basicLand);
            }
        }

        for (int i = 0; i < numBoosterCommon; i++) {
            addToBooster(booster, common);
        }
        for (int i = 0; i < numBoosterUncommon; i++) {
            addToBooster(booster, uncommon);
        }
        for (int i = 0; i < numBoosterRare; i++) {
            if (ratioBoosterMythic > 0 && rnd.nextInt(ratioBoosterMythic) == 1) {
                addToBooster(booster, mythic);
            } else {
                addToBooster(booster, rare);
            }
        }

        if (numBoosterDoubleFaced > 0) {
            this.addDoubleFace(booster);
        }

        if (numBoosterSpecial > 0) {
            int specialCards = 0;
            List<CardInfo> specialMythic = getSpecialMythic();
            if (specialMythic != null) {
                specialCards += specialMythic.size();
            }
            List<CardInfo> specialRare = getSpecialRare();
            if (specialRare != null) {
                specialCards += specialRare.size();
            }
            List<CardInfo> specialUncommon = getSpecialUncommon();
            if (specialUncommon != null) {
                specialCards += specialUncommon.size();
            }
            List<CardInfo> specialCommon = getSpecialCommon();
            if (specialCommon != null) {
                specialCards += specialCommon.size();
            }
            if (specialCards > 0) {
                for (int i = 0; i < numBoosterSpecial; i++) {
                    if (rnd.nextInt(15) < 10) {
                        if (specialCommon != null) {
                            addToBooster(booster, specialCommon);
                        } else {
                            i--;
                        }
                        continue;
                    }
                    if (rnd.nextInt(5) < 4) {
                        if (specialUncommon != null) {
                            addToBooster(booster, specialUncommon);
                        } else {
                            i--;
                        }
                        continue;
                    }
                    if (rnd.nextInt(8) < 7) {
                        if (specialRare != null) {
                            addToBooster(booster, specialRare);
                        } else {
                            i--;
                        }
                        continue;
                    }
                    if (specialMythic != null) {
                        addToBooster(booster, specialMythic);
                    } else {
                        i--;
                    }
                }
            }
        }

        return booster;
    }

    /* add double faced card for Innistrad booster  
     * rarity near as the normal distribution
     */
    private void addDoubleFace(List<Card> booster) {
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes(this.code).doubleFaced(true);
            if (rnd.nextInt(15) < 10) {
                criteria.rarities(Rarity.COMMON);
            } else if (rnd.nextInt(5) < 4) {
                criteria.rarities(Rarity.UNCOMMON);
            } else if (rnd.nextInt(8) < 7) {
                criteria.rarities(Rarity.RARE);
            } else {
                criteria.rarities(Rarity.MYTHIC);
            }
            List<CardInfo> doubleFacedCards = CardRepository.instance.findCards(criteria);
            addToBooster(booster, doubleFacedCards);
        }
    }

    private void addToBooster(List<Card> booster, List<CardInfo> cards) {
        if (!cards.isEmpty()) {
            CardInfo cardInfo = cards.remove(rnd.nextInt(cards.size()));
            if (cardInfo != null) {
                Card card = cardInfo.getCard();
                if (card != null) {
                    booster.add(card);
                }
            }
        }
    }
    
    public boolean hasBoosters() {
        return hasBoosters;
    }
    
    public boolean hasBasicLands() {
        return hasBasicLands;
    }

    public List<CardInfo> getCommon() {
        CardCriteria criteria = new CardCriteria();
        criteria.setCodes(this.code).rarities(Rarity.COMMON).doubleFaced(false);
        return CardRepository.instance.findCards(criteria);
    }

    public List<CardInfo> getSpecialCommon() {
        return null;
    }
    public List<CardInfo> getSpecialUncommon() {
        return null;
    }
    public List<CardInfo> getSpecialRare() {
        return null;
    }
    public List<CardInfo> getSpecialMythic() {
        return null;
    }
}
