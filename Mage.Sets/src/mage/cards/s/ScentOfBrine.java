package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public final class ScentOfBrine extends CardImpl {

    public ScentOfBrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Reveal any number of blue cards in your hand. Counter target spell unless its controller pays {1} for each card revealed this way.
        this.getSpellAbility().addEffect(new ScentOfBrineEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ScentOfBrine(final ScentOfBrine card) {
        super(card);
    }

    @Override
    public ScentOfBrine copy() {
        return new ScentOfBrine(this);
    }
}

class ScentOfBrineEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("any number of blue cards in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public ScentOfBrineEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal any number of blue cards in your hand. "
                + "Counter target spell unless its controller pays {1} for each card revealed this way";
    }

    private ScentOfBrineEffect(final ScentOfBrineEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfBrineEffect copy() {
        return new ScentOfBrineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter));
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        int xValue = cost.getNumberRevealedCards();
        return new CounterUnlessPaysEffect(new GenericManaCost(xValue)).apply(game, source);
    }
}
