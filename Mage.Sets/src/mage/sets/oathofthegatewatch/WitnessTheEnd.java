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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class WitnessTheEnd extends CardImpl {

    public WitnessTheEnd(UUID ownerId) {
        super(ownerId, 82, "Witness the End", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "OGW";

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Target opponent exiles two cards from his or her hand and loses 2 life.
        getSpellAbility().addEffect(new ExileFromZoneTargetEffect(Zone.HAND, null, "", new FilterCard("cards"), 2));
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("and loses 2 life");
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(effect);
    }

    public WitnessTheEnd(final WitnessTheEnd card) {
        super(card);
    }

    @Override
    public WitnessTheEnd copy() {
        return new WitnessTheEnd(this);
    }
}
