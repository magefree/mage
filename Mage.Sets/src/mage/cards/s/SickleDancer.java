
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class SickleDancer extends CardImpl {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "Warrior creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SickleDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Sickle Dancer attacks, if your team controls another Warrior, Sickle Dancer gets +1/+1 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "Whenever {this} attacks, if your team controls another Warrior, {this} gets +1/+1 until end of turn."
        ));
    }

    private SickleDancer(final SickleDancer card) {
        super(card);
    }

    @Override
    public SickleDancer copy() {
        return new SickleDancer(this);
    }
}
