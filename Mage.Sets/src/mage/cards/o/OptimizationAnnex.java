package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class OptimizationAnnex extends CardImpl {

    public OptimizationAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // If one or more charge counters would be put on an artifact you control, that many plus one charge counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, CounterType.CHARGE
        )));
    }

    private OptimizationAnnex(final OptimizationAnnex card) {
        super(card);
    }

    @Override
    public OptimizationAnnex copy() {
        return new OptimizationAnnex(this);
    }
}
