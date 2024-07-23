package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiredClaw extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.LIZARD, "Lizards");

    public HiredClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you attack with one or more Lizards, Hired Claw deals 1 damage to target opponent.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new DamageTargetEffect(1), 1, filter
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {1}{R}: Put a +1/+1 counter on Hired Claw. Activate this ability only if an opponent has lost life this turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new ManaCostsImpl<>("{1}{R}"), 1, OpponentsLostLifeCondition.instance
        ).addHint(OpponentsLostLifeHint.instance));
    }

    private HiredClaw(final HiredClaw card) {
        super(card);
    }

    @Override
    public HiredClaw copy() {
        return new HiredClaw(this);
    }
}
