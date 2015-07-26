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

package mage.sets;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public class FateReforged extends ExpansionSet {

    private static final FateReforged fINSTANCE =  new FateReforged();

    List<CardInfo> savedSpecialRares = new ArrayList<>();

    public static FateReforged getInstance() {
        return fINSTANCE;
    }

    private FateReforged() {
        super("Fate Reforged", "FRF", "mage.sets.fatereforged", new GregorianCalendar(2015, 1, 23).getTime(), SetType.EXPANSION);
        this.blockName = "Khans of Tarkir";
        this.parentSet = KhansOfTarkir.getInstance();
        this.hasBasicLands = false;
        this.hasBoosters = true;
        this.numBoosterSpecial = 1;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
    }

    @Override
    public List<CardInfo> getCardsByRarity(Rarity rarity) {
        if (rarity.equals(Rarity.COMMON)) {
            List<CardInfo> savedCardsInfos = savedCards.get(rarity);
            if (savedCardsInfos == null) {
                CardCriteria criteria = new CardCriteria();
                criteria.setCodes(this.code).notTypes(CardType.LAND);
                criteria.rarities(rarity).doubleFaced(false);
                if (maxCardNumberInBooster != Integer.MAX_VALUE) {
                    criteria.maxCardNumber(maxCardNumberInBooster);
                }
                savedCardsInfos = CardRepository.instance.findCards(criteria);
                savedCards.put(rarity, savedCardsInfos);
            }
            // Return a copy of the saved cards information, as not to modify the original.
            return new ArrayList<>(savedCardsInfos);
        } else {
            return super.getCardsByRarity(rarity);
        }
    }

    @Override
    public List<CardInfo> getSpecialCommon() {
        CardCriteria criteria = new CardCriteria();
        criteria.rarities(Rarity.COMMON).setCodes(this.code).types(CardType.LAND);
        return CardRepository.instance.findCards(criteria);
    }

    @Override
    public List<CardInfo> getSpecialRare() {
        List<CardInfo> specialRares = new ArrayList<>();
        if (savedSpecialRares.isEmpty()) {
            CardCriteria criteria = new CardCriteria();
            criteria.setCodes("KTK").name("Bloodstained Mire");
            savedSpecialRares.addAll(CardRepository.instance.findCards(criteria));
            criteria = new CardCriteria();
            criteria.setCodes("KTK").name("Flooded Strand");
            savedSpecialRares.addAll(CardRepository.instance.findCards(criteria));
            criteria = new CardCriteria();
            criteria.setCodes("KTK").name("Polluted Delta");
            savedSpecialRares.addAll(CardRepository.instance.findCards(criteria));
            criteria = new CardCriteria();
            criteria.setCodes("KTK").name("Windswept Heath");
            savedSpecialRares.addAll(CardRepository.instance.findCards(criteria));
            criteria = new CardCriteria();
            criteria.setCodes("KTK").name("Wooded Foothills");
            savedSpecialRares.addAll(CardRepository.instance.findCards(criteria));
        }
        specialRares.addAll(savedSpecialRares);
        return specialRares;
    }      
}
