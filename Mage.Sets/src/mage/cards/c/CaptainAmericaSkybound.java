package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;

import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainAmericaSkybound extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature");

    public CaptainAmericaSkybound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Battalion -- Whenever Captain America and at least two other creatures attack, put a +1/+1 counter on each attacking creature. They gain indestructible until end of turn.
        Ability ability = new BattalionAbility(new AddCountersAllEffect(
            CounterType.P1P1.createInstance(), filter
        ));
        ability.addEffect(new GainAbilityAllEffect(
            IndestructibleAbility.getInstance(), Duration.EndOfTurn,
            filter, "They gain indestructible until end of turn"
        ));
        this.addAbility(ability);
    }

    private CaptainAmericaSkybound(final CaptainAmericaSkybound card) {
        super(card);
    }

    @Override
    public CaptainAmericaSkybound copy() {
        return new CaptainAmericaSkybound(this);
    }
}
