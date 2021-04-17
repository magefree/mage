package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildwoodTracker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public WildwoodTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Wildwood Tracker attacks or blocks, if you control another non-Human creature, Wildwood Tracker gets +1/+1 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksOrBlocksTriggeredAbility(
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn), false
                ), condition, "Whenever {this} attacks or blocks, if you control another non-Human creature, " +
                "{this} gets +1/+1 until end of turn."
        ));
    }

    private WildwoodTracker(final WildwoodTracker card) {
        super(card);
    }

    @Override
    public WildwoodTracker copy() {
        return new WildwoodTracker(this);
    }
}
