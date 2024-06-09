package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class ScentOfJasmine extends CardImpl {

    public ScentOfJasmine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.
        this.getSpellAbility().addEffect(new ScentOfJasmineEffect());
    }

    private ScentOfJasmine(final ScentOfJasmine card) {
        super(card);
    }

    @Override
    public ScentOfJasmine copy() {
        return new ScentOfJasmine(this);
    }
}

class ScentOfJasmineEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of white cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ScentOfJasmineEffect() {
        super(Outcome.GainLife);
        this.staticText = "reveal any number of white cards in your hand. "
                + "You gain 2 life for each card revealed this way";
    }

    private ScentOfJasmineEffect(final ScentOfJasmineEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfJasmineEffect copy() {
        return new ScentOfJasmineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new GainLifeEffect(2 * xValue).apply(game, source);
    }
}
