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
package mage.sets.torment;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author anonymous
 */
public class RancidEarth extends CardImpl {

    public RancidEarth(UUID ownerId) {
        super(ownerId, 78, "Rancid Earth", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");
        this.expansionSetCode = "TOR";

        // Destroy target land.
        // Threshold - If seven or more cards are in your graveyard, instead destroy that land and Rancid Earth deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RancidEarthEffect(),
                new DestroyTargetEffect(),
                new CardsInControllerGraveCondition(7),
                "Destroy target land.<br/><br/><i>Threshold<i/> - If seven or more cards are in your graveyard, instead destroy that land and Rancid Earth deals 1 damage to each creature and each player"));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public RancidEarth(final RancidEarth card) {
        super(card);
    }

    @Override
    public RancidEarth copy() {
        return new RancidEarth(this);
    }
}

class RancidEarthEffect extends OneShotEffect {

    public RancidEarthEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy that land and Rancid Earth deals 1 damage to each creature and each player";
    }

    public RancidEarthEffect(final RancidEarthEffect effect) {
        super(effect);
    }

    @Override
    public RancidEarthEffect copy() {
        return new RancidEarthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect1 = new DestroyTargetEffect("destroy that land");
        if (effect1.apply(game, source)) {
            return new DamageEverythingEffect(1).apply(game, source);
        }

        return false;
    }
}
