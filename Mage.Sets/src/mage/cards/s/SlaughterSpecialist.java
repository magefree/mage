package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlaughterSpecialist extends CardImpl {

    public SlaughterSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Slaughter Specialist enters the battlefield, each opponent creates a 1/1 white Human creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenAllEffect(new HumanToken(), TargetController.OPPONENT)
        ));

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Slaughter Specialist.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private SlaughterSpecialist(final SlaughterSpecialist card) {
        super(card);
    }

    @Override
    public SlaughterSpecialist copy() {
        return new SlaughterSpecialist(this);
    }
}
