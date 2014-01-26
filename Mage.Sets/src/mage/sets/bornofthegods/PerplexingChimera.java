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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * You may exchange control of Perplexing Chimera and any spell cast
 * by an opponent, not just one with targets.
 *
 * You make the decision whether to exchange control of Perplexing Chimera
 * and the spell as the triggered ability resolves.
 *
 * If Perplexing Chimera leaves the battlefield or the spell leaves the stack
 * before the triggered ability resolves, you can't make the exchange.
 *
 * Neither Perplexing Chimera nor the spell changes zones. Only control of
 * them is exchanged.
 *
 * After the ability resolves, you control the spell. Any instance of "you"
 * in that spell's text now refers to you, "an opponent" refers to one of
 * your opponents, and so on. The change of control happens before new targets
 * are chosen, so any targeting restrictions such as "target opponent" or
 * "target creature you control" are now made in reference to you, not the
 * spell's original controller. You may change those targets to be legal in
 * reference to you, or, if those are the spell's only targets, the spell will
 * be countered on resolution for having illegal targets. When the spell
 * resolves, any illegal targets are unaffected by it and you make all decisions
 * the spell's effect calls for.
 *
 * You may change any of the spell's targets. If you change a target, you must
 * choose a legal target for the spell. If you can't, you must leave the target
 * the same (even if that target is now illegal).
 *
 * Gaining control of a spell and changing its targets won't cause any heroic
 * abilities of the new targets to trigger.
 *
 * If you gain control of an instant or sorcery spell, it will be put into its
 * owner's graveyard as it resolves or is countered.
 *
 * In some unusual cases, you may not control Perplexing Chimera when its
 * triggered ability resolves (perhaps because the triggered ability triggered
 * again and resolved while the original ability was on the stack). In these
 * cases, you can exchange control of Perplexing Chimera and the spell that
 * causes the ability to trigger, even if you control neither of them. If you
 * do, you'll be able to change targets of the spell, not the spell's new
 * controller.
 *
 *
 * @author LevelX2
 */
public class PerplexingChimera extends CardImpl<PerplexingChimera> {

    public PerplexingChimera(UUID ownerId) {
        super(ownerId, 48, "Perplexing Chimera", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Chimera");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent casts a spell, you may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell.
        this.addAbility(new PerplexingChimeraTriggeredAbility());
    }

    public PerplexingChimera(final PerplexingChimera card) {
        super(card);
    }

    @Override
    public PerplexingChimera copy() {
        return new PerplexingChimera(this);
    }
}

class PerplexingChimeraTriggeredAbility extends TriggeredAbilityImpl<PerplexingChimeraTriggeredAbility> {

    public PerplexingChimeraTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PerplexingChimeraControlExchangeEffect(), true);
    }

    public PerplexingChimeraTriggeredAbility(final PerplexingChimeraTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            for (Effect effect: this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, " + super.getRule();
    }

    @Override
    public PerplexingChimeraTriggeredAbility copy() {
        return new PerplexingChimeraTriggeredAbility(this);
    }
}

class PerplexingChimeraControlExchangeEffect extends OneShotEffect<PerplexingChimeraControlExchangeEffect> {

    public PerplexingChimeraControlExchangeEffect() {
        super(Outcome.Benefit);
        this.staticText = "exchange control of {this} and that spell. If you do, you may choose new targets for the spell";
    }

    public PerplexingChimeraControlExchangeEffect(final PerplexingChimeraControlExchangeEffect effect) {
        super(effect);
    }

    @Override
    public PerplexingChimeraControlExchangeEffect copy() {
        return new PerplexingChimeraControlExchangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (spell != null && controller != null) {
            Player spellCaster = game.getPlayer(spell.getControllerId());
            // controller gets controll of spell
            spell.setControllerId(controller.getId());
            // and chooses new targets
            spell.chooseNewTargets(game, controller.getId());
            game.informPlayers(new StringBuilder(controller.getName()).append(" got control of ").append(spell.getName()).append(" spell.").toString());
            // and spell controller get control of Perplexing Chimera
            if (spellCaster != null) {
                ContinuousEffect effect = new PerplexingChimeraControlEffect();
                effect.setTargetPointer(new FixedTarget(spellCaster.getId()));
                game.addEffect(effect, source);
            }
        }

        return false;
    }
}

class PerplexingChimeraControlEffect extends ContinuousEffectImpl<PerplexingChimeraControlEffect> {

    public PerplexingChimeraControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "PerplexingChimeraControlEffect";
    }

    public PerplexingChimeraControlEffect(final PerplexingChimeraControlEffect effect) {
        super(effect);
    }

    @Override
    public PerplexingChimeraControlEffect copy() {
        return new PerplexingChimeraControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(this.getTargetPointer().getFirst(game, source), game);
        }
        return false;
    }

}
