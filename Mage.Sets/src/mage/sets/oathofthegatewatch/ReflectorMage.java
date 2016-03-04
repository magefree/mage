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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ReflectorMage extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ReflectorMage(UUID ownerId) {
        super(ownerId, 157, "Reflector Mage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. That creature's owner can't cast spells with the same name as that creature until your next turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReflectorMageEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public ReflectorMage(final ReflectorMage card) {
        super(card);
    }

    @Override
    public ReflectorMage copy() {
        return new ReflectorMage(this);
    }
}

class ReflectorMageEffect extends OneShotEffect {

    public ReflectorMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature an opponent controls to its owner's hand. That creature's owner can't cast spells with the same name as that creature until your next turn";
    }

    public ReflectorMageEffect(final ReflectorMageEffect effect) {
        super(effect);
    }

    @Override
    public ReflectorMageEffect copy() {
        return new ReflectorMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                controller.moveCards(targetCreature, Zone.HAND, source, game);
                if (!targetCreature.getName().isEmpty()) { // if the creature had no name, no restrict effect will be created
                    game.addEffect(new ExclusionRitualReplacementEffect(targetCreature.getName(), targetCreature.getOwnerId()), source);
                }
            }
            return true;
        }
        return false;
    }
}

class ExclusionRitualReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    private final String creatureName;
    private final UUID ownerId;

    ExclusionRitualReplacementEffect(String creatureName, UUID ownerId) {
        super(Duration.Custom, Outcome.Detriment);
        staticText = "That creature's owner can't cast spells with the same name as that creature until your next turn";
        this.creatureName = creatureName;
        this.ownerId = ownerId;
    }

    ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
        super(effect);
        this.creatureName = effect.creatureName;
        this.ownerId = effect.ownerId;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Spell spell = game.getState().getStack().getSpell(event.getSourceId());
            if (spell != null && spell.isFaceDown(game)) {
                return false; // Face Down cast spell (Morph creature) has no name
            }
            return card.getName().equals(creatureName);
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.getActivePlayerId().equals(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}
