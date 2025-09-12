package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DualSunTechnique extends CardImpl {

    public DualSunTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature you control gains double strike until end of turn. If it has a +1/+1 counter on it, draw a card.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DualSunTechniqueEffect());
    }

    private DualSunTechnique(final DualSunTechnique card) {
        super(card);
    }

    @Override
    public DualSunTechnique copy() {
        return new DualSunTechnique(this);
    }
}

class DualSunTechniqueEffect extends OneShotEffect {

    DualSunTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "If it has a +1/+1 counter on it, draw a card";
    }

    private DualSunTechniqueEffect(final DualSunTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public DualSunTechniqueEffect copy() {
        return new DualSunTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.getCounters(game).containsKey(CounterType.P1P1))
                .map(x -> source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.drawCards(1, source, game) > 0)
                .isPresent();
    }
}
