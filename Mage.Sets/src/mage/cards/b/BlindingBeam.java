
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BlindingBeam extends CardImpl {

    public BlindingBeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Tap two target creatures;
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2,2));
        // or creatures don't untap during target player's next untap step.
        Mode mode = new Mode(new BlindingBeamEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}
        this.addAbility(new EntwineAbility("{1}"));
    }

    private BlindingBeam(final BlindingBeam card) {
        super(card);
    }

    @Override
    public BlindingBeam copy() {
        return new BlindingBeam(this);
    }
}

class BlindingBeamEffect extends OneShotEffect {

    public BlindingBeamEffect() {
        super(Outcome.Tap);
        staticText = "creatures don't untap during target player's next untap step";
    }

    public BlindingBeamEffect(final BlindingBeamEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {                
            game.addEffect(new BlindingBeamEffect2(player.getId()), source);
            return true;
        }
        return false;
    }

    @Override
    public BlindingBeamEffect copy() {
        return new BlindingBeamEffect(this);
    }

}

class BlindingBeamEffect2 extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    private final UUID targetPlayerId;

    public BlindingBeamEffect2(UUID targetPlayerId) {
        super(Duration.Custom, Outcome.Detriment);
        this.targetPlayerId = targetPlayerId;
    }

    public BlindingBeamEffect2(final BlindingBeamEffect2 effect) {
        super(effect);
        this.targetPlayerId = effect.targetPlayerId;
    }

    @Override
    public BlindingBeamEffect2 copy() {
        return new BlindingBeamEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        // the PRE step part is directly after the UNTAP events for permanents
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.isActivePlayer(targetPlayerId) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // prevent untap event of creatures of target player
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isControlledBy(targetPlayerId) && filter.match(permanent, game)) {
                return true;
            }
        }
        return false;
    }

}
