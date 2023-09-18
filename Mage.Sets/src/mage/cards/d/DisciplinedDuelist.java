package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisciplinedDuelist extends CardImpl {

    public DisciplinedDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Disciplined Duelist enters the battlefield with a shield counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.SHIELD.createInstance(1)),
                "with a shield counter on it. <i>(If it would be dealt damage " +
                        "or destroyed, remove a shield counter from it instead.)</i>"
        ));
    }

    private DisciplinedDuelist(final DisciplinedDuelist card) {
        super(card);
    }

    @Override
    public DisciplinedDuelist copy() {
        return new DisciplinedDuelist(this);
    }
}
