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
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ArchitectsOfWill extends CardImpl {

    public ArchitectsOfWill(UUID ownerId) {
        super(ownerId, 17, "Architects of Will", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Architects of Will enters the battlefield, look at the top three cards of target player's library, then put them back in any order.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ArchitectsOfWillEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Cycling {UB}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{U/B}")));
    }

    public ArchitectsOfWill(final ArchitectsOfWill card) {
        super(card);
    }

    @Override
    public ArchitectsOfWill copy() {
        return new ArchitectsOfWill(this);
    }
}

class ArchitectsOfWillEffect extends OneShotEffect {

    public ArchitectsOfWillEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order";
    }

    public ArchitectsOfWillEffect(final ArchitectsOfWillEffect effect) {
        super(effect);
    }

    @Override
    public ArchitectsOfWillEffect copy() {
        return new ArchitectsOfWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null
                || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.LIBRARY);
        int count = Math.min(targetPlayer.getLibrary().size(), 3);
        for (int i = 0; i < count; i++) {
            Card card = targetPlayer.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
            }
        }
        controller.lookAtCards("Architects of Will", cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        return true;
    }
}
