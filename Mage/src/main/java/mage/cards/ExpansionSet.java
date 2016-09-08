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
import java.util.EnumMap;
import java.util.List;
import java.util.Random;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class ExpansionSet implements Serializable {


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
    protected int ratioBoosterSpecialLand = 0; // if > 0 basic lands are replaced with special land in the ratio every X land is replaced by special land

    protected int numBoosterCommon;
    protected int numBoosterUncommon;
    protected int numBoosterRare;
    protected int numBoosterDoubleFaced; // -1 = include normally 0 = exclude  1-n = include explicit
    protected int ratioBoosterMythic;

    protected String packageName;
    protected int maxCardNumberInBooster; // used to ommit cards with collector numbers beyond the regular cards in a set for boosters

    protected final EnumMap<Rarity, List<CardInfo>> savedCards;

    public ExpansionSet(String name, String code, String packageName, Date releaseDate, SetType setType) {
        this.name = name;
        this.code = code;
        this.releaseDate = releaseDate;
        this.setType = setType;
        this.packageName = packageName;
        this.maxCardNumberInBooster = Integer.MAX_VALUE;
        savedCards = new EnumMap<>(Rarity.class);
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

    public List<Card> create15CardBooster() {
        // Forces 15 card booster packs.
        // if the packs are too small, it adds commons to fill it out.
        // if the packs are too big, it removes the first cards.
        // since it adds lands then commons before uncommons
        // and rares this should be the least disruptive.
        List<Card> theBooster = this.createBooster();
        List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);

        while (15 > theBooster.size()) {
            addToBooster(theBooster, commons);
        }

        while (theBooster.size() > 15) {
            theBooster.remove(0);
        }

        return theBooster;
    }

    protected void addToBooster(List<Card> booster, List<CardInfo> cards) {
        if (!cards.isEmpty()) {
            CardInfo cardInfo = cards.remove(RandomUtil.nextInt(cards.size()));
            if (cardInfo != null) {
                Card card = cardInfo.getCard();
                if (card != null) {
                    booster.add(card);
                }
            }
        }
    }

    public List<Card> createBooster() {
        List<Card> booster = new ArrayList<>();
        if (!hasBoosters) {
            return booster;
        }

        if (numBoosterLands > 0) {
            List<CardInfo> specialLands = getSpecialLand();
            List<CardInfo> basicLands = getCardsByRarity(Rarity.LAND);
            for (int i = 0; i < numBoosterLands; i++) {
                if (ratioBoosterSpecialLand > 0 && RandomUtil.nextInt(ratioBoosterSpecialLand) == 0 && specialLands != null) {
                    addToBooster(booster, specialLands);
                } else {
                    addToBooster(booster, basicLands);
                }
            }
        }
        int numSpecialCommons = getNumberOfSpecialCommons();
        int numCommonsToGenerate = numBoosterCommon - numSpecialCommons;

        List<CardInfo> commons = getCardsByRarity(Rarity.COMMON);
        for (int i = 0; i < numCommonsToGenerate; i++) {
            addToBooster(booster, commons);
        }

        if (numSpecialCommons > 0) { // e.g. used to conditionaly replace common cards in the booster
            addSpecialCommon(booster, numSpecialCommons);
        }

        List<CardInfo> uncommons = getCardsByRarity(Rarity.UNCOMMON);
        for (int i = 0; i < numBoosterUncommon; i++) {
            addToBooster(booster, uncommons);
        }

        List<CardInfo> rares = getCardsByRarity(Rarity.RARE);
        List<CardInfo> mythics = getCardsByRarity(Rarity.MYTHIC);
        for (int i = 0; i < numBoosterRare; i++) {
            if (ratioBoosterMythic > 0 && RandomUtil.nextInt(ratioBoosterMythic) == 0) {
                addToBooster(booster, mythics);
            } else {
                addToBooster(booster, rares);
            }
        }

        if (numBoosterDoubleFaced > 0) {
            addDoubleFace(booster);
        }

        if (numBoosterSpecial > 0) {
            addSpecial(booster);
        }

        return booster;
    }

    /* add double faced card for Innistrad booster
     * rarity near as the normal distribution
     */
    public void addDoubleFace(List<Card> booster) {
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes(this.code).doubleFaced(true);
            if (RandomUtil.nextInt(15) < 10) {
                criteria.rarities(Rarity.COMMON);
            } else if (RandomUtil.nextInt(5) < 4) {
                criteria.rarities(Rarity.UNCOMMON);
            } else if (RandomUtil.nextInt(8) < 7) {
                criteria.rarities(Rarity.RARE);
            } else {
                criteria.rarities(Rarity.MYTHIC);
            }
            List<CardInfo> doubleFacedCards = CardRepository.instance.findCards(criteria);
            addToBooster(booster, doubleFacedCards);
        }
    }

    /**
     * Can be overwritten if sometimes special cards will be generated instead
     * of common slots
     *
     * @return
     */
    public int getNumberOfSpecialCommons() {
        return 0;
    }

    /**
     * Can be overwritten to add a replacement for common card in boosters
     *
     * @param booster
     */
    public void addSpecialCommon(List<Card> booster, int number) {

    }

    private void addSpecial(List<Card> booster) {
        int specialCards = 0;
        List<CardInfo> specialBonus = getSpecialBonus();
        if (specialBonus != null) {
            specialCards += specialBonus.size();
        }
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
                if (RandomUtil.nextInt(15) < 10) {
                    if (specialCommon != null && !specialCommon.isEmpty()) {
                        addToBooster(booster, specialCommon);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (RandomUtil.nextInt(4) < 3) {
                    if (specialUncommon != null && !specialUncommon.isEmpty()) {
                        addToBooster(booster, specialUncommon);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (RandomUtil.nextInt(8) < 7) {
                    if (specialRare != null && !specialRare.isEmpty()) {
                        addToBooster(booster, specialRare);
                    } else {
                        i--;
                    }
                    continue;
                }
                if (specialMythic != null && !specialMythic.isEmpty()) {
                    if (specialBonus != null && !specialBonus.isEmpty()) {
                        if (RandomUtil.nextInt(3) < 2) {
                            addToBooster(booster, specialMythic);
                            continue;
                        }
                    } else {
                        addToBooster(booster, specialMythic);
                        continue;
                    }
                } else {
                    i--;
                }
                if (specialBonus != null && !specialBonus.isEmpty()) {
                    addToBooster(booster, specialBonus);
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

    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        List<CardInfo> savedCardsInfos = savedCards.get(rarity);
        if (savedCardsInfos == null) {
            CardCriteria criteria = new CardCriteria();
            if (rarity.equals(Rarity.LAND)) {
                criteria.setCodes(!hasBasicLands && parentSet != null ? parentSet.code : this.code);
            } else {
                criteria.setCodes(this.code);
            }
            criteria.rarities(rarity);
            if (numBoosterDoubleFaced > -1) {
                criteria.doubleFaced(false);
            }
            if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                criteria.maxCardNumber(maxCardNumberInBooster);
            }
            savedCardsInfos = CardRepository.instance.findCards(criteria);
            savedCards.put(rarity, savedCardsInfos);
        }
        // Return a copy of the saved cards information, as not to let modify the original.
        return new ArrayList<>(savedCardsInfos);
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

    public List<CardInfo> getSpecialBonus() {
        return null;
    }

    public List<CardInfo> getSpecialLand() {
        return null;
    }

    public void removeSavedCards() {
        savedCards.clear();
    }

}
