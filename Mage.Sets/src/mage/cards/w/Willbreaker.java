package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Willbreaker extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("a spell or ability you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Willbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, gain control of that creature for as long as you control Willbreaker.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE, filter, SetTargetPointer.PERMANENT, false));
    }

    private Willbreaker(final Willbreaker card) {
        super(card);
    }

    @Override
    public Willbreaker copy() {
        return new Willbreaker(this);
    }
}
