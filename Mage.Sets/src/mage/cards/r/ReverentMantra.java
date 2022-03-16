package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOwnedCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class ReverentMantra extends CardImpl {

    private static final FilterOwnedCard filter
            = new FilterOwnedCard("a white card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ReverentMantra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // You may exile a white card from your hand rather than pay Reverent Mantra's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(filter))));

        // Choose a color. All creatures gain protection from the chosen color until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorAllEffect(
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ));
    }

    private ReverentMantra(final ReverentMantra card) {
        super(card);
    }

    @Override
    public ReverentMantra copy() {
        return new ReverentMantra(this);
    }
}
