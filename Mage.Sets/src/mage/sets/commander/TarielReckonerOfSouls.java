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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class TarielReckonerOfSouls extends CardImpl {

    public TarielReckonerOfSouls(UUID ownerId) {
        super(ownerId, 229, "Tariel, Reckoner of Souls", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{W}{B}{R}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // {T}: Choose a creature card at random from target opponent's graveyard. Put that card onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TarielReckonerOfSoulsEffect(), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public TarielReckonerOfSouls(final TarielReckonerOfSouls card) {
        super(card);
    }

    @Override
    public TarielReckonerOfSouls copy() {
        return new TarielReckonerOfSouls(this);
    }
}

class TarielReckonerOfSoulsEffect extends OneShotEffect {

    public TarielReckonerOfSoulsEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Choose a creature card at random from target opponent's graveyard. Put that card onto the battlefield under your control";
    }

    public TarielReckonerOfSoulsEffect(final TarielReckonerOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public TarielReckonerOfSoulsEffect copy() {
        return new TarielReckonerOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            Cards creatureCards = new CardsImpl();
            for (Card card : targetOpponent.getGraveyard().getCards(new FilterCreatureCard(), game)) {
                creatureCards.add(card);
            }
            if (!creatureCards.isEmpty()) {
                Card card = creatureCards.getRandom(game);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
            }
            return true;
        }
        return false;
    }
}
