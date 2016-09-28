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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author Styxo
 */
public class MarchOfTheDroids extends CardImpl {

    public MarchOfTheDroids(UUID ownerId) {
        super(ownerId, 206, "March of the Droids", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{W}{U}{B}");
        this.expansionSetCode = "SWS";

        // Remove all repair counters from each exiled card you own. You may cast each card with repair counter removed this way without paying its mana cost until end of turn.
        this.getSpellAbility().addEffect(new MarchOfTheDroidsEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public MarchOfTheDroids(final MarchOfTheDroids card) {
        super(card);
    }

    @Override
    public MarchOfTheDroids copy() {
        return new MarchOfTheDroids(this);
    }
}

class MarchOfTheDroidsEffect extends OneShotEffect {

    public MarchOfTheDroidsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all repair counters from each exiled card you own. You may cast each card with repair counter removed this way without paying its mana cost until end of turn";
    }

    public MarchOfTheDroidsEffect(final MarchOfTheDroidsEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfTheDroidsEffect copy() {
        return new MarchOfTheDroidsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards castable = new CardsImpl();
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.getOwnerId().equals(controller.getId()) && card.getCounters(game).getCount(CounterType.REPAIR) > 0) {
                    int number = card.getCounters(game).getCount(CounterType.REPAIR);
                    if (number > 0) {
                        castable.add(card);
                        card.removeCounters(CounterType.REPAIR.createInstance(number), game);
                    }
                }
            }
            if (!castable.isEmpty()) {
                ContinuousEffect effect = new MarchOfTheDroidsCastFromExileEffect();
                effect.setTargetPointer(new FixedTargets(castable, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class MarchOfTheDroidsCastFromExileEffect extends AsThoughEffectImpl {

    public MarchOfTheDroidsCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may play the card from exile without paying its mana cost until end of turn";
    }

    public MarchOfTheDroidsCastFromExileEffect(final MarchOfTheDroidsCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MarchOfTheDroidsCastFromExileEffect copy() {
        return new MarchOfTheDroidsCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(objectId)) {
            Card card = game.getCard(objectId);
            Player controller = game.getPlayer(source.getControllerId());
            if (card != null && controller != null) {
                if (!card.getCardType().contains(CardType.LAND)) {
                    controller.setCastSourceIdWithAlternateMana(objectId, null, null);
                    return true;
                }
            }
        }
        return false;
    }
}
