package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VoraciousWurm extends CardImpl {

    public VoraciousWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Voracious Wurm enters the battlefield with X +1/+1 counters on it, where X is the amount of life you've gained this turn.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), ControllerGainedLifeCount.instance, true
        ), "with X +1/+1 counters on it, where X is the amount of life you've gained this turn")
                .addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private VoraciousWurm(final VoraciousWurm card) {
        super(card);
    }

    @Override
    public VoraciousWurm copy() {
        return new VoraciousWurm(this);
    }
}
