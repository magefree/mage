package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.VillainToken;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class ConstructACosmicCube extends CardImpl {

    public ConstructACosmicCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.PLAN);

        // Whenever you draw your second card each turn, create a 2/1 black Villain creature token with menace and put a plan counter on this enchantment.
        Ability ability = new DrawNthCardTriggeredAbility(new CreateTokenEffect(new VillainToken()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the seventh plan counter is put on this enchantment, sacrifice it. When you do, you control target opponent during their next turn.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new ControlTargetPlayerNextTurnEffect(), false,
                "you control target opponent during their next turn"
        );
        reflexive.addTarget(new TargetOpponent());
        this.addAbility(new PlanCounterThresholdTriggeredAbility(7, reflexive));
    }

    private ConstructACosmicCube(final ConstructACosmicCube card) {
        super(card);
    }

    @Override
    public ConstructACosmicCube copy() {
        return new ConstructACosmicCube(this);
    }
}
