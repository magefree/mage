package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BolgEreborsReckoning extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Goblins and Orcs you control");

    static {
        filter.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate()
        ));
    }

    public BolgEreborsReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of each combat, other Goblins and Orcs you control get +2/+2 until end of turn. Creatures your opponents control get -1/-1 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            TargetController.ANY,
            new BoostControlledEffect(2, 2, Duration.EndOfTurn, filter, true),
            false
        );
        ability.addEffect(new BoostAllEffect(
            -1, -1, Duration.EndOfTurn,
            StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));
        this.addAbility(ability);
    }

    private BolgEreborsReckoning(final BolgEreborsReckoning card) {
        super(card);
    }

    @Override
    public BolgEreborsReckoning copy() {
        return new BolgEreborsReckoning(this);
    }
}
