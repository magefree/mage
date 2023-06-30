
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetAtBeginningOfNextEndStepEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LoneFox
 */
public final class BloodFrenzy extends CardImpl {

    public BloodFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Cast Blood Frenzy only before the combat damage step.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new BloodFrenzyCastRestriction());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Target attacking or blocking creature gets +4/+0 until end of turn. Destroy that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DestroyTargetAtBeginningOfNextEndStepEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private BloodFrenzy(final BloodFrenzy card) {
        super(card);
    }

    @Override
    public BloodFrenzy copy() {
        return new BloodFrenzy(this);
    }
}


class BloodFrenzyCastRestriction extends ContinuousRuleModifyingEffectImpl {

    BloodFrenzyCastRestriction() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only before the combat damage step";
    }

    BloodFrenzyCastRestriction(final BloodFrenzyCastRestriction effect) {
        super(effect);
    }

    @Override
    public BloodFrenzyCastRestriction copy() {
        return new BloodFrenzyCastRestriction(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getSourceId().equals(source.getSourceId())) {
            if(game.getTurnPhaseType() == TurnPhase.COMBAT
                // There cannot be a legal target before declare attackers,
                // so in practice it is limited to these two steps.
                && (game.getTurnStepType() == PhaseStep.DECLARE_ATTACKERS
                || game.getTurnStepType() == PhaseStep.DECLARE_BLOCKERS)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
