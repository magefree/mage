
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Thrive extends CardImpl {

    public Thrive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Put a +1/+1 counter on each of X target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on each of X target creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(ThriveAdjuster.instance);
    }

    private Thrive(final Thrive card) {
        super(card);
    }

    @Override
    public Thrive copy() {
        return new Thrive(this);
    }
}

enum ThriveAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}