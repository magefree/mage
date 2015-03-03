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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class HellholeRats extends CardImpl {

    public HellholeRats(UUID ownerId) {
        super(ownerId, 113, "Hellhole Rats", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Rat");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Hellhole Rats enters the battlefield, target player discards a card. Hellhole Rats deals damage to that player equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HellholeRatsEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public HellholeRats(final HellholeRats card) {
        super(card);
    }

    @Override
    public HellholeRats copy() {
        return new HellholeRats(this);
    }
}

class HellholeRatsEffect extends OneShotEffect {

    public HellholeRatsEffect() {
        super(Outcome.Damage);
        this.staticText = "target player discards a card. {this} deals damage to that player equal to that card's converted mana cost";
    }

    public HellholeRatsEffect(final HellholeRatsEffect effect) {
        super(effect);
    }

    @Override
    public HellholeRatsEffect copy() {
        return new HellholeRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            Cards cards = targetPlayer.discard(1, false, source, game);
            if (!cards.isEmpty()) {
                for (Card card : cards.getCards(game)) {
                    damage = card.getManaCost().convertedManaCost();
                }
                targetPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
