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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class SpinalEmbrace extends CardImpl<SpinalEmbrace> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public SpinalEmbrace(UUID ownerId) {
        super(ownerId, 276, "Spinal Embrace", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "INV";

        this.color.setBlue(true);

        // Cast Spinal Embrace only during combat.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpinalEmbraceEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Untap target creature you don't control and gain control of it. It gains haste until end of turn. At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new SpinalEmbraceAddDelayedEffect());
    }

    public SpinalEmbrace(final SpinalEmbrace card) {
        super(card);
    }

    @Override
    public SpinalEmbrace copy() {
        return new SpinalEmbrace(this);
    }
}

class SpinalEmbraceEffect extends ReplacementEffectImpl<SpinalEmbraceEffect> {
    SpinalEmbraceEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during combat";
    }

    SpinalEmbraceEffect(final SpinalEmbraceEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.CAST_SPELL) && event.getSourceId().equals(source.getSourceId())) {
            return !game.getTurn().getPhaseType().equals(TurnPhase.COMBAT);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SpinalEmbraceEffect copy() {
        return new SpinalEmbraceEffect(this);
    }
}

class SpinalEmbraceAddDelayedEffect extends OneShotEffect<SpinalEmbraceAddDelayedEffect> {

    public SpinalEmbraceAddDelayedEffect() {
        super(Outcome.Sacrifice);
        staticText = "At the beginning of the next end step, sacrifice it. If you do, you gain life equal to its toughness";
    }

    public SpinalEmbraceAddDelayedEffect(final SpinalEmbraceAddDelayedEffect effect) {
        super(effect);
    }

    @Override
    public SpinalEmbraceAddDelayedEffect copy() {
        return new SpinalEmbraceAddDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpinalEmbraceSacrificeEffect sacrificeEffect = new SpinalEmbraceSacrificeEffect();
        sacrificeEffect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}

class SpinalEmbraceSacrificeEffect extends OneShotEffect<SpinalEmbraceSacrificeEffect> {

    public SpinalEmbraceSacrificeEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice it. If you do, you gain life equal to its toughness";
    }

    public SpinalEmbraceSacrificeEffect(final SpinalEmbraceSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SpinalEmbraceSacrificeEffect copy() {
        return new SpinalEmbraceSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
                affectedTargets++;
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.gainLife(permanent.getPower().getValue(), game);
                }
            }
        }
        return affectedTargets > 0;
    }
}
