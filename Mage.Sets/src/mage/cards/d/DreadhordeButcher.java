package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadhordeButcher extends CardImpl {

    public DreadhordeButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Dreadhorde Butcher deals combat damage to a player or planeswalker, put a +1/+1 counter on Dreadhorde Butcher.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()
        ), false));

        // When Dreadhorde Butcher dies, it deals damage equal to its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(
                new SourcePermanentPowerCount()
        ).setText("it deals damage equal to its power to any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private DreadhordeButcher(final DreadhordeButcher card) {
        super(card);
    }

    @Override
    public DreadhordeButcher copy() {
        return new DreadhordeButcher(this);
    }
}
