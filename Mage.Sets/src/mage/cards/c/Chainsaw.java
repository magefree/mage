package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DiesOneOrMoreCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Chainsaw extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.REV);

    public Chainsaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Chainsaw enters, it deals 3 damage to up to one target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever one or more creatures die, put a rev counter on Chainsaw.
        this.addAbility(new DiesOneOrMoreCreaturesTriggeredAbility(new AddCountersSourceEffect(CounterType.REV.createInstance())));

        // Equipped creature gets +X/+0, where X is the number of rev counters on Chainsaw.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, StaticValue.get(0))));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private Chainsaw(final Chainsaw card) {
        super(card);
    }

    @Override
    public Chainsaw copy() {
        return new Chainsaw(this);
    }
}
