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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDiscard;

/**
 *
 * @author jeffwadsworth
 */
public class SpellboundDragon extends CardImpl<SpellboundDragon> {

    public SpellboundDragon(UUID ownerId) {
        super(ownerId, 90, "Spellbound Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Spellbound Dragon attacks, draw a card, then discard a card. Spellbound Dragon gets +X/+0 until end of turn, where X is the discarded card's converted mana cost.
        this.addAbility(new AttacksTriggeredAbility(new SpellboundDragonEffect(), false));

    }

    public SpellboundDragon(final SpellboundDragon card) {
        super(card);
    }

    @Override
    public SpellboundDragon copy() {
        return new SpellboundDragon(this);
    }
}

class SpellboundDragonEffect extends OneShotEffect<SpellboundDragonEffect> {

    public SpellboundDragonEffect() {
        super(Outcome.BoostCreature);
        staticText = "draw a card, then discard a card. Spellbound Dragon gets +X/+0 until end of turn, where X is the discarded card's converted mana cost";
    }

    public SpellboundDragonEffect(final SpellboundDragonEffect effect) {
        super(effect);
    }

    @Override
    public SpellboundDragonEffect copy() {
        return new SpellboundDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent dragon = game.getPermanent(source.getSourceId());
        you.drawCards(1, game);
        TargetDiscard target = new TargetDiscard(you.getId());
        you.choose(Outcome.Discard, target, source.getSourceId(), game);
        Card card = you.getHand().get(target.getFirstTarget(), game);
        if (card != null && you.discard(card, source, game)) {
            int cmc = card.getManaCost().convertedManaCost();
            if (dragon != null) {
                game.addEffect(new BoostSourceEffect(cmc, 0, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }
}