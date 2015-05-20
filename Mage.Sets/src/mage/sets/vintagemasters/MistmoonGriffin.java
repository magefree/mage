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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MistmoonGriffin extends CardImpl {

    public MistmoonGriffin(UUID ownerId) {
        super(ownerId, 34, "Mistmoon Griffin", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "VMA";
        this.subtype.add("Griffin");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Mistmoon Griffin dies, exile Mistmoon Griffin, then return the top creature card of your graveyard to the battlefield.
        Ability ability = new DiesTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new MistmoonGriffinEffect());
        this.addAbility(ability);

    }

    public MistmoonGriffin(final MistmoonGriffin card) {
        super(card);
    }

    @Override
    public MistmoonGriffin copy() {
        return new MistmoonGriffin(this);
    }
}

class MistmoonGriffinEffect extends OneShotEffect {

    public MistmoonGriffinEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then return the top creature card of your graveyard to the battlefield";
    }

    public MistmoonGriffinEffect(final MistmoonGriffinEffect effect) {
        super(effect);
    }

    @Override
    public MistmoonGriffinEffect copy() {
        return new MistmoonGriffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card lastCreatureCard = null;
            for (Card card :controller.getGraveyard().getCards(game)) {
                if (card.getCardType().contains(CardType.CREATURE)) {
                    lastCreatureCard = card;
                }
            }
            if (lastCreatureCard != null) {
                return controller.putOntoBattlefieldWithInfo(lastCreatureCard, game, Zone.GRAVEYARD, source.getSourceId());
            }
            return true;
        }
        return false;
    }
}
