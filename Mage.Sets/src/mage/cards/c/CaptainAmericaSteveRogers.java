package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainAmericaSteveRogers extends CardImpl {

    public CaptainAmericaSteveRogers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Captain America attacks, put a +1/+1 counter on another target creature you control. That creature gains indestructible until end of turn.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
            .setText("That creature gains indestructible until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private CaptainAmericaSteveRogers(final CaptainAmericaSteveRogers card) {
        super(card);
    }

    @Override
    public CaptainAmericaSteveRogers copy() {
        return new CaptainAmericaSteveRogers(this);
    }
}
