package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.DifferentlyNamedPermanentCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ChristineChapelCombatMedic extends CardImpl {

    private static final DifferentlyNamedPermanentCount xValue = new DifferentlyNamedPermanentCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS);

    public ChristineChapelCombatMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Strange new worlds -- When Christine Chapel enters, you gain life equal to the number of differently named lands you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(xValue));
        this.addAbility(ability.addHint(xValue.getHint()).withFlavorWord("Strange new worlds"));

        // Whenever you gain life, put two +1/+1 counters on Christine Chapel.
        this.addAbility(new GainLifeControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2))
        ));
    }

    private ChristineChapelCombatMedic(final ChristineChapelCombatMedic card) {
        super(card);
    }

    @Override
    public ChristineChapelCombatMedic copy() {
        return new ChristineChapelCombatMedic(this);
    }
}
