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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public class ParadoxicalOutcome extends CardImpl {

    private static FilterControlledPermanent filter = new FilterControlledPermanent(new StringBuilder("any number of of target nonland, nontoken permanents you control").toString());

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ParadoxicalOutcome(UUID ownerId) {
        super(ownerId, 60, "Paradoxical Outcome", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "KLD";

        // Return any number of target nonland, nontoken permanents you control to their owners' hands. Draw a card for each card returned to your hand this way.
        this.getSpellAbility().addEffect(new ParadoxicalOutcomeEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, false));
        DynamicValue paradoxicalOutcomeValue = new ParadoxicalOutcomeNumber(false);
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(paradoxicalOutcomeValue));
    }

    public ParadoxicalOutcome(final ParadoxicalOutcome card) {
        super(card);
    }

    @Override
    public ParadoxicalOutcome copy() {
        return new ParadoxicalOutcome(this);
    }
}

class ParadoxicalOutcomeEffect extends OneShotEffect {

    ParadoxicalOutcomeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return any number of target nonland, nontoken permanents you control to their owners' hands. Draw a card for each card returned to your hand this way";
    }

    ParadoxicalOutcomeEffect(final ParadoxicalOutcomeEffect effect) {
        super(effect);
    }

    @Override
    public ParadoxicalOutcomeEffect copy() {
        return new ParadoxicalOutcomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(source.getTargets().get(0).getTargets());
            game.getState().setValue(CardUtil.getCardZoneString("ParadoxicalOutcomeEffect", source.getSourceId(), game), cards.size());
            controller.moveCards(new CardsImpl(source.getTargets().get(0).getTargets()), Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

class ParadoxicalOutcomeNumber implements DynamicValue {

    private int zoneChangeCounter = 0;
    private final boolean previousZone;

    public ParadoxicalOutcomeNumber(boolean previousZone) {
        this.previousZone = previousZone;
    }

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
                if (previousZone) {
                    zoneChangeCounter--;
                }
            }
        }
        int number = 0;
        Integer sweepNumber = (Integer) game.getState().getValue(new StringBuilder("ParadoxicalOutcomeEffect").append(source.getSourceId()).append(zoneChangeCounter).toString());
        if (sweepNumber != null) {
            number = sweepNumber;
        }
        return number;
    }

    @Override
    public ParadoxicalOutcomeNumber copy() {
        return new ParadoxicalOutcomeNumber(previousZone);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of permanents returned this way";
    }
}
