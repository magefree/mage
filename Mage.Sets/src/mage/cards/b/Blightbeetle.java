package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantHaveCountersAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blightbeetle extends CardImpl {

    public Blightbeetle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from green
        this.addAbility(ProtectionAbility.from(ObjectColor.GREEN));

        // Creatures your opponents control can't have +1/+1 counters put on them.
        this.addAbility(new SimpleStaticAbility(new CantHaveCountersAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, CounterType.P1P1
        )));
    }

    private Blightbeetle(final Blightbeetle card) {
        super(card);
    }

    @Override
    public Blightbeetle copy() {
        return new Blightbeetle(this);
    }
}
