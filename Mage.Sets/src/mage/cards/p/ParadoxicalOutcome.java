
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class ParadoxicalOutcome extends CardImpl {

    private static FilterControlledPermanent filter = new FilterControlledPermanent("any number of target nonland, nontoken permanents you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TokenPredicate.FALSE);
    }

    public ParadoxicalOutcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Return any number of target nonland, nontoken permanents you control to their owners' hands. Draw a card for each card returned to your hand this way.
        this.getSpellAbility().addEffect(new ParadoxicalOutcomeEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, false));
        DynamicValue paradoxicalOutcomeValue = new ParadoxicalOutcomeNumber(false);
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(paradoxicalOutcomeValue));
    }

    private ParadoxicalOutcome(final ParadoxicalOutcome card) {
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

    private ParadoxicalOutcomeEffect(final ParadoxicalOutcomeEffect effect) {
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
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (zoneChangeCounter == 0) {
            Card card = game.getCard(sourceAbility.getSourceId());
            if (card != null) {
                zoneChangeCounter = card.getZoneChangeCounter(game);
                if (previousZone) {
                    zoneChangeCounter--;
                }
            }
        }
        int number = 0;
        Integer sweepNumber = (Integer) game.getState().getValue("ParadoxicalOutcomeEffect" + sourceAbility.getSourceId() + zoneChangeCounter);
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
