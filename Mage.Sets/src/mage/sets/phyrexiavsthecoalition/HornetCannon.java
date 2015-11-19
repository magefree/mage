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
package mage.sets.phyrexiavsthecoalition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HornetToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author FenrisulfrX
 */
public class HornetCannon extends CardImpl {

    public HornetCannon(UUID ownerId) {
        super(ownerId, 28, "Hornet Cannon", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "DDE";

        // {3}, {tap}: Put a 1/1 colorless Insect artifact creature token with flying and haste named Hornet onto the battlefield. Destroy it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HornetCannonEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public HornetCannon(final HornetCannon card) {
        super(card);
    }

    @Override
    public HornetCannon copy() {
        return new HornetCannon(this);
    }
}

class HornetCannonEffect extends OneShotEffect {

    public HornetCannonEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put a 1/1 colorless Insect artifact creature token with flying and haste named Hornet onto the battlefield. Destroy it at the beginning of the next end step.";
    }

    public HornetCannonEffect(final HornetCannonEffect effect) {
        super(effect);
    }

    @Override
    public HornetCannonEffect copy() {
        return new HornetCannonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token hornetToken = new HornetToken();
        hornetToken.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        for (UUID tokenId : hornetToken.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                DestroyTargetEffect destroyEffect = new DestroyTargetEffect(false);
                destroyEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(destroyEffect), source);
            }
        }
        return true;
    }
}
