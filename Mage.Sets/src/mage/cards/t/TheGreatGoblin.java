package mage.cards.t;

import java.util.UUID;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheGreatGoblin extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Goblin, Orc, or Army you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another Goblin, Orc, or Army you control");

    static {
        filter.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate(),
            SubType.ARMY.getPredicate()
        ));
        filter2.add(AnotherPredicate.instance);
        filter2.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate(),
            SubType.ARMY.getPredicate()
        ));
    }

    public TheGreatGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you put one or more counters on a Goblin, Orc, or Army you control, The Great Goblin deals 2 damage to target opponent.
        Ability ability = new PutCounterOnPermanentTriggeredAbility(
            new DamageTargetEffect(2),
            null, // Any counter type
            filter
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Whenever another Goblin, Orc, or Army you control dies, exile the top card of your library. You may play it until the end of your next turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn).withTextOptions("it", true),
            false, filter2
        ));
    }

    private TheGreatGoblin(final TheGreatGoblin card) {
        super(card);
    }

    @Override
    public TheGreatGoblin copy() {
        return new TheGreatGoblin(this);
    }
}
