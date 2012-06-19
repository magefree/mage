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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PlayTargetWithoutPayingManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author BetaSteward
 */
public class ChancellorOfTheSpires extends CardImpl<ChancellorOfTheSpires> {

    private static final String abilityText = "at the beginning of the first upkeep, each opponent puts the top seven cards of his or her library into his or her graveyard";

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.getCardType().add(CardType.INSTANT);
        filter.getCardType().add(CardType.SORCERY);
        filter.setScopeCardType(ComparisonScope.Any);
    }

    public ChancellorOfTheSpires(UUID ownerId) {
        super(ownerId, 31, "Chancellor of the Spires", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}{U}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, each opponent puts the top seven cards of his or her library into his or her graveyard.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheSpiresDelayedTriggeredAbility(), abilityText));

        this.addAbility(FlyingAbility.getInstance());

        // When Chancellor of the Spires enters the battlefield, you may cast target instant or sorcery card from an opponent's graveyard without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PlayTargetWithoutPayingManaEffect(), true);
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    public ChancellorOfTheSpires(final ChancellorOfTheSpires card) {
        super(card);
    }

    @Override
    public ChancellorOfTheSpires copy() {
        return new ChancellorOfTheSpires(this);
    }
}

class ChancellorOfTheSpiresDelayedTriggeredAbility extends DelayedTriggeredAbility<ChancellorOfTheSpiresDelayedTriggeredAbility> {

    ChancellorOfTheSpiresDelayedTriggeredAbility () {
        super(new ChancellorOfTheSpiresEffect());
    }

    ChancellorOfTheSpiresDelayedTriggeredAbility(ChancellorOfTheSpiresDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE) {
            return true;
        }
        return false;
    }
    @Override
    public ChancellorOfTheSpiresDelayedTriggeredAbility copy() {
        return new ChancellorOfTheSpiresDelayedTriggeredAbility(this);
    }
}

class ChancellorOfTheSpiresEffect extends OneShotEffect<ChancellorOfTheSpiresEffect> {

    ChancellorOfTheSpiresEffect () {
        super(Outcome.Benefit);
        staticText = "each opponent puts the top seven cards of his or her library into his or her graveyard";
    }

    ChancellorOfTheSpiresEffect(ChancellorOfTheSpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                int cardsCount = Math.min(7, player.getLibrary().size());
                for (int i = 0; i < cardsCount; i++) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null)
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    else
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public ChancellorOfTheSpiresEffect copy() {
        return new ChancellorOfTheSpiresEffect(this);
    }

}

