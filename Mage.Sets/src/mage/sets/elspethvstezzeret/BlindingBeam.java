/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.elspethvstezzeret;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
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
public class BlindingBeam extends CardImpl<BlindingBeam> {

    public BlindingBeam(UUID ownerId) {
        super(ownerId, 28, "Blinding Beam", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "DDF";

        this.color.setWhite(true);

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Tap two target creatures;
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2,2));
        // or creatures don't untap during target player's next untap step.
        Mode mode = new Mode();
        mode.getEffects().add(new BlindingBeamEffect());
        mode.getTargets().add(new TargetPlayer(true));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}
        this.addAbility(new EntwineAbility("{1}"));
    }

    public BlindingBeam(final BlindingBeam card) {
        super(card);
    }

    @Override
    public BlindingBeam copy() {
        return new BlindingBeam(this);
    }
}

class BlindingBeamEffect extends OneShotEffect<BlindingBeamEffect> {

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

class BlindingBeamEffect2 extends ReplacementEffectImpl<BlindingBeamEffect2> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    private UUID targetPlayerId;

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
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(targetPlayerId) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // replace untap event of creatures of target player
        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == EventType.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(targetPlayerId) && filter.match(permanent, game)) {
                return true;
            }
        }
        return false;
    }

}
