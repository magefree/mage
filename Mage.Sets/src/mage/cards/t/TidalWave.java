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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nigelzor
 */
public class TidalWave extends CardImpl {

    public TidalWave(UUID ownerId) {
        super(ownerId, 100, "Tidal Wave", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "MIR";

        // Put a 5/5 blue Wall creature token with defender onto the battlefield. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new TidalWaveEffect());
    }

    public TidalWave(final TidalWave card) {
        super(card);
    }

    @Override
    public TidalWave copy() {
        return new TidalWave(this);
    }
}

class TidalWaveEffect extends OneShotEffect {

    public TidalWaveEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 5/5 blue Wall creature token with defender onto the battlefield. Sacrifice it at the beginning of the next end step.";
    }

    public TidalWaveEffect(TidalWaveEffect effect) {
        super(effect);
    }

    @Override
    public TidalWaveEffect copy() {
        return new TidalWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new WallToken();
        if (token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId())) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect();
                    sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
                }
            }
            return true;
        }
        return false;
    }
}

class WallToken extends Token {

    WallToken() {
        super("Wall", "5/5 blue Wall creature token with defender");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Wall");
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(DefenderAbility.getInstance());
    }
}
