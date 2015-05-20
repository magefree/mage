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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class CragganwickCremator extends CardImpl {

    public CragganwickCremator(UUID ownerId) {
        super(ownerId, 87, "Cragganwick Cremator", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Giant");
        this.subtype.add("Shaman");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Cragganwick Cremator enters the battlefield, discard a card at random. If you discard a creature card this way, Cragganwick Cremator deals damage equal to that card's power to target player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CragganwickCrematorEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public CragganwickCremator(final CragganwickCremator card) {
        super(card);
    }

    @Override
    public CragganwickCremator copy() {
        return new CragganwickCremator(this);
    }
}

class CragganwickCrematorEffect extends OneShotEffect {

    public CragganwickCrematorEffect() {
        super(Outcome.Neutral);
        this.staticText = "discard a card at random. If you discard a creature card this way, {this} deals damage equal to that card's power to target player";
    }

    public CragganwickCrematorEffect(final CragganwickCrematorEffect effect) {
        super(effect);
    }

    @Override
    public CragganwickCrematorEffect copy() {
        return new CragganwickCrematorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetedPlayer = game.getPlayer(source.getFirstTarget());
        if (you != null && targetedPlayer != null) {
            Card discardedCard = targetedPlayer.discardOne(true, source, game);
            if (discardedCard != null
                    && discardedCard.getCardType().contains(CardType.CREATURE)) {
                int damage = discardedCard.getPower().getValue();
                targetedPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
