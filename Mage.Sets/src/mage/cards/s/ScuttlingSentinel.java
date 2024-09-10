package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ScuttlingSentinel extends CardImpl {

    public ScuttlingSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");
        
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Scuttling Sentinel enters the battlefield, put a +1/+1 counter on another target creature you control.
        // Until end of turn, that creature becomes a blue Crab in addition to its other types and gains hexproof.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        ability.addEffect(new BecomesColorTargetEffect(ObjectColor.BLUE, Duration.EndOfTurn)
                .setText("until end of turn, that creature becomes a blue"));
        ability.addEffect(new AddCardSubTypeTargetEffect(SubType.CRAB, Duration.EndOfTurn)
                .setText(" Crab in addition to its other types"));
        ability.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("and gains hexproof"));
        this.addAbility(ability);

    }

    private ScuttlingSentinel(final ScuttlingSentinel card) {
        super(card);
    }

    @Override
    public ScuttlingSentinel copy() {
        return new ScuttlingSentinel(this);
    }
}
