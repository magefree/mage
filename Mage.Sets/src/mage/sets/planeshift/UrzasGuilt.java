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
package mage.sets.planeshift;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.effects.common.DiscardEachPlayerEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.LoseLifeAllEffect;
import mage.cards.CardImpl;

/**
 *
 * @author LevelX2
 */
public class UrzasGuilt extends CardImpl<UrzasGuilt> {

    public UrzasGuilt(UUID ownerId) {
        super(ownerId, 130, "Urza's Guilt", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");
        this.expansionSetCode = "PLS";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Each player draws two cards, then discards three cards, then loses 4 life.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(2));
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(3, false));
        this.getSpellAbility().addEffect(new LoseLifeAllEffect(4));

    }

    public UrzasGuilt(final UrzasGuilt card) {
        super(card);
    }

    @Override
    public UrzasGuilt copy() {
        return new UrzasGuilt(this);
    }
}
