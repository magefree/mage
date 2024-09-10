package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class PetrifyingMeddler extends CardImpl {

    public PetrifyingMeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, tap up to one target creature and put a stun counter on it.
        Ability ability = new CastSourceTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private PetrifyingMeddler(final PetrifyingMeddler card) {
        super(card);
    }

    @Override
    public PetrifyingMeddler copy() {
        return new PetrifyingMeddler(this);
    }
}
