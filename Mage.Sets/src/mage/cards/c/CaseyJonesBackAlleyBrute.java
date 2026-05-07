package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaseyJonesBackAlleyBrute extends CardImpl {

    private static final DynamicValue xValue = new EffectKeyValue("countersAdded", "that much");

    public CaseyJonesBackAlleyBrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Casey Jones attacks, put a +1/+1 counter on target attacking creature.
        Ability ability = new AttacksTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);

        // Whenever you put one or more +1/+1 counters on a creature you control, Casey Jones deals that much damage to target opponent.
        Ability ability2 = new PutCounterOnPermanentTriggeredAbility(
            new DamageTargetEffect(xValue), CounterType.P1P1, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED
        );
        ability2.addTarget(new TargetOpponent());
        this.addAbility(ability2);
    }

    private CaseyJonesBackAlleyBrute(final CaseyJonesBackAlleyBrute card) {
        super(card);
    }

    @Override
    public CaseyJonesBackAlleyBrute copy() {
        return new CaseyJonesBackAlleyBrute(this);
    }
}
