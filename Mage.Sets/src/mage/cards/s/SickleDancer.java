package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SickleDancer extends CardImpl {

    private static final FilterPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "your team controls another Warrior");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, false);

    public SickleDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Sickle Dancer attacks, if your team controls another Warrior, Sickle Dancer gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)).withInterveningIf(condition));
    }

    private SickleDancer(final SickleDancer card) {
        super(card);
    }

    @Override
    public SickleDancer copy() {
        return new SickleDancer(this);
    }
}
