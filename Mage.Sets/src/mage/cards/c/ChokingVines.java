package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import static mage.filter.StaticFilters.FILTER_ATTACKING_CREATURES;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author arcox
 */
public final class ChokingVines extends CardImpl {

    public ChokingVines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Cast only during the declare blockers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null,
                PhaseStep.DECLARE_BLOCKERS, null, "Cast this spell only during the declare blockers step"));

        // X target attacking creatures become blocked. Choking Vines deals 1 damage to each of those creatures.
        this.getSpellAbility().addEffect(new ChokingVinesEffect());
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().setTargetAdjuster(ChokingVinesAdjuster.instance);
    }

    public ChokingVines(final ChokingVines card) {
        super(card);
    }

    @Override
    public ChokingVines copy() {
        return new ChokingVines(this);
    }
}

enum ChokingVinesAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int x = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(x, x, FILTER_ATTACKING_CREATURES, false));
    }
}

class ChokingVinesEffect extends OneShotEffect {

    public ChokingVinesEffect() {
        super(Outcome.Benefit);
        this.staticText = "X target attacking creatures become blocked";
    }

    public ChokingVinesEffect(final ChokingVinesEffect effect) {
        super(effect);
    }

    @Override
    public ChokingVinesEffect copy() {
        return new ChokingVinesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Targets targets = source.getTargets();

        if (controller != null && !targets.isEmpty()) {
            for (Target target : targets) {
                for (UUID id : target.getTargets()) {
                    CombatGroup combatGroup = game.getCombat().findGroup(id);
                    if (combatGroup != null) {
                        combatGroup.setBlocked(true); // non-banded creatures
                        combatGroup.setBlocked(true, game); // this only works for banded creatures and needs to be checked out
                        Permanent attacker = game.getPermanent(id);
                        if (attacker != null) {
                            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_BLOCKED, attacker.getId(), null));
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
