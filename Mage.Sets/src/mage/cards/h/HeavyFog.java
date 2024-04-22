
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 *
 * @author L_J
 */
public final class HeavyFog extends CardImpl {

    public HeavyFog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Cast Deep Wood only during the declare attackers step and only if you've been attacked this step.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only during the declare attackers step and only if you've been attacked this step."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // Prevent all damage that would be dealt to you this turn by attacking creatures.
        this.getSpellAbility().addEffect(new HeavyFogEffect());
    }

    private HeavyFog(final HeavyFog card) {
        super(card);
    }

    @Override
    public HeavyFog copy() {
        return new HeavyFog(this);
    }
}

class HeavyFogEffect extends PreventionEffectImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature();

    HeavyFogEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to you this turn by attacking creatures";
    }

    private HeavyFogEffect(final HeavyFogEffect effect) {
        super(effect);
    }

    @Override
    public HeavyFogEffect copy() {
        return new HeavyFogEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamagePlayerEvent && event.getAmount() > 0) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (event.getTargetId().equals(source.getControllerId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
