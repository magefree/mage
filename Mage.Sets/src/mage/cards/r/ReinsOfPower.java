package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class ReinsOfPower extends CardImpl {

    public ReinsOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Untap all creatures you control and all creatures target opponent controls. You and that opponent each gain control of all creatures the other controls until end of turn. Those creatures gain haste until end of turn.
        this.getSpellAbility().addEffect(new ReinsOfPowerEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ReinsOfPower(final ReinsOfPower card) {
        super(card);
    }

    @Override
    public ReinsOfPower copy() {
        return new ReinsOfPower(this);
    }
}

class ReinsOfPowerEffect extends OneShotEffect {

    ReinsOfPowerEffect() {
        super(Outcome.Benefit);
        this.staticText = "Untap all creatures you control and all creatures target opponent controls. You and that opponent each gain control of all creatures the other controls until end of turn. Those creatures gain haste until end of turn";
    }

    ReinsOfPowerEffect(final ReinsOfPowerEffect effect) {
        super(effect);
    }

    @Override
    public ReinsOfPowerEffect copy() {
        return new ReinsOfPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID opponentId = this.getTargetPointer().getFirst(game, source);
        if (opponentId != null) {
            // Untap all creatures you control and all creatures target opponent controls.
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(new ControllerIdPredicate(source.getControllerId()), new ControllerIdPredicate(opponentId)));
            new UntapAllEffect(filter).apply(game, source);

            // You and that opponent each gain control of all creatures the other controls until end of turn.
            Set<UUID> yourCreatures = new HashSet<>();
            Set<UUID> opponentCreatures = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source, game)) {
                yourCreatures.add(permanent.getId());
            }
            FilterCreaturePermanent filterOpponent = new FilterCreaturePermanent();
            filterOpponent.add(new ControllerIdPredicate(opponentId));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filterOpponent, source.getControllerId(), source, game)) {
                opponentCreatures.add(permanent.getId());
            }
            for (UUID creatureId : yourCreatures) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn, opponentId);
                effect.setTargetPointer(new FixedTarget(creatureId, game));
                game.addEffect(effect, source);
            }
            for (UUID creatureId : opponentCreatures) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creatureId, game));
                game.addEffect(effect, source);
            }

            // Those creatures gain haste until end of turn.
            game.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter), source);

            return true;
        }
        return false;
    }
}
