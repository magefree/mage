package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HogMonkeyRampage extends CardImpl {

    public HogMonkeyRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R/G}");

        // Choose target creature you control and target creature an opponent controls. Put a +1/+1 counter on the creature you control if it has power 4 or greater. Then those creatures fight each other.
        this.getSpellAbility().addEffect(new HogMonkeyRampageEffect());
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText("Then those creatures fight each other"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private HogMonkeyRampage(final HogMonkeyRampage card) {
        super(card);
    }

    @Override
    public HogMonkeyRampage copy() {
        return new HogMonkeyRampage(this);
    }
}

class HogMonkeyRampageEffect extends OneShotEffect {

    HogMonkeyRampageEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control and target creature an opponent controls. " +
                "Put a +1/+1 counter on the creature you control if it has power 4 or greater";
    }

    private HogMonkeyRampageEffect(final HogMonkeyRampageEffect effect) {
        super(effect);
    }

    @Override
    public HogMonkeyRampageEffect copy() {
        return new HogMonkeyRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null
                && permanent.getPower().getValue() >= 4
                && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}
