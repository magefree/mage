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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author fireshoes
 */
public class PermeatingMass extends CardImpl {

    public PermeatingMass(UUID ownerId) {
        super(ownerId, 165, "Permeating Mass", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Spirit");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Permeating Mass deals combat damage to a creature, that creature becomes a copy of Permeating Mass.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new PermeatingMassEffect(), false, true));
    }

    public PermeatingMass(final PermeatingMass card) {
        super(card);
    }

    @Override
    public PermeatingMass copy() {
        return new PermeatingMass(this);
    }
}

class PermeatingMassEffect extends OneShotEffect {

    public PermeatingMassEffect() {
        super(Outcome.Copy);
        this.staticText = "that creature becomes a copy of {this}.";
    }

    public PermeatingMassEffect(final PermeatingMassEffect effect) {
        super(effect);
    }

    @Override
    public PermeatingMassEffect copy() {
        return new PermeatingMassEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability ability) {
        Permanent copyTo = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, ability));
        if (copyTo != null) {
            Permanent copyFrom = (Permanent) ability.getSourceObject(game);
            if (copyFrom != null) {
                game.copyPermanent(Duration.Custom, copyFrom, copyTo.getId(), ability, new EmptyApplyToPermanent());
            }
        }
        return true;
    }
}
