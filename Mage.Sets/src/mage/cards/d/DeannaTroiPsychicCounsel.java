package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class DeannaTroiPsychicCounsel extends CardImpl {

    public DeannaTroiPsychicCounsel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BETAZOID);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other creatures you control enter with an additional +1/+1 counter on them.
        this.addAbility(new SimpleStaticAbility(new EntersWithCountersControlledEffect(
            StaticFilters.FILTER_PERMANENT_CREATURE, CounterType.P1P1.createInstance(), true
        ).setText("other creatures you control enter with an additional +1/+1 counter on them")));
    }

    private DeannaTroiPsychicCounsel(final DeannaTroiPsychicCounsel card) {
        super(card);
    }

    @Override
    public DeannaTroiPsychicCounsel copy() {
        return new DeannaTroiPsychicCounsel(this);
    }
}
