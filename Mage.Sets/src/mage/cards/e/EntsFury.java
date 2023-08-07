package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TiagoMDG
 */
public final class EntsFury extends CardImpl {
    public EntsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{1}{G}");

        // Put a +1/+1 counter on target creature you control if its power is 4 or greater.
        // Then that creature gets +1/+1 until end of turn and fights target
        // creature you don't control.

        this.getSpellAbility().addEffect(new EntsFuryEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private EntsFury(final EntsFury card) {
        super(card);
    }

    @Override
    public EntsFury copy() {
        return new EntsFury(this);
    }
}

class EntsFuryEffect extends OneShotEffect {

    public EntsFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a +1/+1 counter on target creature you control if its power is 4 or greater. " +
                "Then that creature gets +1/+1 until end of turn and fights target creature you don't control.";
    }

    public EntsFuryEffect(final EntsFuryEffect effect) {
        super(effect);
    }

    @Override
    public EntsFuryEffect copy() {
        return new EntsFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null){
            return false;
        }

        // Checks if the creature's power is 4 or greater
        if (permanent.getPower().getValue() >= 4){
            permanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
        }

        // Adds temporary +1/+1 until end of turn and adds fight effect
        ContinuousEffect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);
        return new FightTargetsEffect(false).apply(game, source);
    }
}
