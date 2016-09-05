/*
 * Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.List;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

/**
 *
 * @author fireshoes
 */
public class ShadowsOverInnistrad extends ExpansionSet {

    private static final ShadowsOverInnistrad fINSTANCE = new ShadowsOverInnistrad();

    public static ShadowsOverInnistrad getInstance() {
        return fINSTANCE;
    }

    protected final EnumMap<Rarity, List<CardInfo>> savedDoubleFacedCards;

    private ShadowsOverInnistrad() {
        super("Shadows over Innistrad", "SOI", "mage.sets.shadowsoverinnistrad", new GregorianCalendar(2016, 3, 8).getTime(), SetType.EXPANSION);
        this.blockName = "Shadows over Innistrad";
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;
        savedDoubleFacedCards = new EnumMap<>(Rarity.class);
    }

    /* add double faced card for SOI booster
     * add only common or uncommon
     */
    @Override
    public void addDoubleFace(List<Card> booster) {
        for (int i = 0; i < numBoosterDoubleFaced; i++) {
            List<CardInfo> doubleFacedCards;
            if (RandomUtil.nextInt(15) < 10) {
                doubleFacedCards = getDoubleFacedCardsByRarity(Rarity.COMMON);
            } else {
                doubleFacedCards = getDoubleFacedCardsByRarity(Rarity.UNCOMMON);
            }
            addToBooster(booster, doubleFacedCards);
        }
    }

    public List<CardInfo> getDoubleFacedCardsByRarity(Rarity rarity) {
        List<CardInfo> savedCardsInfos = savedDoubleFacedCards.get(rarity);
        if (savedCardsInfos == null) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes(getCode());
            criteria.rarities(rarity);
            criteria.doubleFaced(true);
            savedCardsInfos = CardRepository.instance.findCards(criteria);
            savedDoubleFacedCards.put(rarity, savedCardsInfos);
        }
        // Return a copy of the saved cards information, as not to let modify the original.
        return new ArrayList<>(savedCardsInfos);
    }

    @Override
    public int getNumberOfSpecialCommons() {
        // Then about an eighth of the packs will have a second double-faced card, which will be a rare or mythic rare.
        return RandomUtil.nextInt(8) == 0 ? 1 : 0;
    }

    @Override
    public void addSpecialCommon(List<Card> booster, int number) {
        // number is here always 1
        List<CardInfo> doubleFacedCards;
        if (RandomUtil.nextInt(8) > 0) {
            doubleFacedCards = getDoubleFacedCardsByRarity(Rarity.RARE);
        } else {
            doubleFacedCards = getDoubleFacedCardsByRarity(Rarity.MYTHIC);
        }
        addToBooster(booster, doubleFacedCards);
    }

}
