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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class SurvivalCache extends CardImpl<SurvivalCache> {

    public SurvivalCache(UUID ownerId) {
        super(ownerId, 48, "Survival Cache", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "ROE";

        this.color.setWhite(true);

        // You gain 2 life. Then if you have more life than an opponent, draw a card.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new SurvivalCacheEffect());
        this.addAbility(new ReboundAbility());
    }

    public SurvivalCache(final SurvivalCache card) {
        super(card);
    }

    @Override
    public SurvivalCache copy() {
        return new SurvivalCache(this);
    }
}

class SurvivalCacheEffect extends OneShotEffect<SurvivalCacheEffect> {
    SurvivalCacheEffect() {
        super(Constants.Outcome.DrawCard);
        staticText = "Then if you have more life than an opponent, draw a card";
    }

    SurvivalCacheEffect(final SurvivalCacheEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer != null) {
            boolean haveMoreLife = false;
            for (UUID id : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(id);
                if (opponent != null && opponent.getLife() < sourcePlayer.getLife()) {
                    haveMoreLife = true;
                    break;
                }
            }
            if (haveMoreLife)
                sourcePlayer.drawCards(1, game);
        }
        return false;
    }

    @Override
    public SurvivalCacheEffect copy() {
        return new SurvivalCacheEffect(this);
    }
}