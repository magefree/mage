package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class PracticedOffense extends CardImpl {

    public PracticedOffense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Put a +1/+1 counter on each creature target player controls. Target creature gains your choice of double strike or lifelink until end of turn.
        this.getSpellAbility().addEffect(new PracticedOffenseEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainsChoiceOfAbilitiesEffect(
           DoubleStrikeAbility.getInstance(), LifelinkAbility.getInstance()
        ).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {1}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{W}")));
    }

    private PracticedOffense(final PracticedOffense card) {
        super(card);
    }

    @Override
    public PracticedOffense copy() {
        return new PracticedOffense(this);
    }
}

class PracticedOffenseEffect extends OneShotEffect {

    PracticedOffenseEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each creature target player controls";
    }

    private PracticedOffenseEffect(final PracticedOffenseEffect effect) {
        super(effect);
    }

    @Override
    public PracticedOffenseEffect copy() {
        return new PracticedOffenseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_CONTROLLED_CREATURE,
            getTargetPointer().getFirst(game, source), source, game
        )) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        return true;
    }
}
