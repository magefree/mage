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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public class SingeMindOgre extends CardImpl<SingeMindOgre> {

    public SingeMindOgre(UUID ownerId) {
        super(ownerId, 45, "Singe-Mind Ogre", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Ogre");
        this.subtype.add("Mutant");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Singe-Mind Ogre enters the battlefield, target player reveals a card at random from his or her hand, then loses life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SingeMindOgreEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    public SingeMindOgre(final SingeMindOgre card) {
        super(card);
    }

    @Override
    public SingeMindOgre copy() {
        return new SingeMindOgre(this);
    }
}

class SingeMindOgreEffect extends OneShotEffect<SingeMindOgreEffect> {

    public SingeMindOgreEffect() {
        super(Outcome.LoseLife);
        this.staticText = "target player reveals a card at random from his or her hand, then loses life equal to that card's converted mana cost";
    }

    public SingeMindOgreEffect(final SingeMindOgreEffect effect) {
        super(effect);
    }

    @Override
    public SingeMindOgreEffect copy() {
        return new SingeMindOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && targetPlayer.getHand().size() > 0) {
            Cards revealed = new CardsImpl();
            Card card = targetPlayer.getHand().getRandom(game);
            revealed.add(card);
            targetPlayer.revealCards("Ignite Memories", revealed, game);
            targetPlayer.loseLife(card.getManaCost().convertedManaCost(), game);
            return true;
        }
        return false;
    }
}
