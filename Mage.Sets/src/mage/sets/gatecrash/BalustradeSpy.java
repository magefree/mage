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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class BalustradeSpy extends CardImpl<BalustradeSpy> {

    public BalustradeSpy(UUID ownerId) {
        super(ownerId, 57, "Balustrade Spy", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Vampire");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Balustrade Spy enters the battlefield, target player reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BalustradeSpyEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public BalustradeSpy(final BalustradeSpy card) {
        super(card);
    }

    @Override
    public BalustradeSpy copy() {
        return new BalustradeSpy(this);
    }
}

class BalustradeSpyEffect extends OneShotEffect<BalustradeSpyEffect> {

    public BalustradeSpyEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals cards from the top of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard";
    }

    public BalustradeSpyEffect(final BalustradeSpyEffect effect) {
        super(effect);
    }

    @Override
    public BalustradeSpyEffect copy() {
        return new BalustradeSpyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        CardsImpl cards = new CardsImpl();
        boolean landFound = false;
        while (player.getLibrary().size() > 0 && !landFound) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                if (card.getCardType().contains(CardType.LAND)) {
                    landFound = true;
                }
            }
        }
        if (!cards.isEmpty()) {
            player.revealCards("Balustrade Spy", cards, game);
            return true;
        }
        return true;
    }
}
