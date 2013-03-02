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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class PermafrostTrap extends CardImpl<PermafrostTrap> {

    public PermafrostTrap(UUID ownerId) {
        super(ownerId, 34, "Permafrost Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setBlue(true);

        // If an opponent had a green creature enter the battlefield under his or her control this turn, you may pay {U} rather than pay Permafrost Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new PermafrostTrapAlternativeCost());

        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, 2);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new PermafrostTrapEffect());

        this.addWatcher(new PermafrostTrapWatcher());
    }

    public PermafrostTrap(final PermafrostTrap card) {
        super(card);
    }

    @Override
    public PermafrostTrap copy() {
        return new PermafrostTrap(this);
    }
}

class PermafrostTrapWatcher extends WatcherImpl<PermafrostTrapWatcher> {

    public PermafrostTrapWatcher() {
        super("PermafrostTrapWatcher", Constants.WatcherScope.GAME);
    }

    public PermafrostTrapWatcher(final PermafrostTrapWatcher watcher) {
        super(watcher);
    }

    @Override
    public PermafrostTrapWatcher copy() {
        return new PermafrostTrapWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { // no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent perm = game.getPermanent(event.getTargetId());
            if (perm.getCardType().contains(CardType.CREATURE) && perm.getColor().contains(ObjectColor.GREEN) && !perm.getControllerId().equals(controllerId)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}

class PermafrostTrapAlternativeCost extends AlternativeCostImpl<PermafrostTrapAlternativeCost> {

    public PermafrostTrapAlternativeCost() {
        super("you may pay {U} rather than pay Permafrost Trap's mana cost");
        this.add(new ColoredManaCost(Constants.ColoredManaSymbol.U));
    }

    public PermafrostTrapAlternativeCost(final PermafrostTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public PermafrostTrapAlternativeCost copy() {
        return new PermafrostTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        PermafrostTrapWatcher watcher = (PermafrostTrapWatcher) game.getState().getWatchers().get("PermafrostTrapWatcher");
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If an opponent had a green creature enter the battlefield under his or her control this turn, you may pay {U} rather than pay Permafrost Trap's mana cost";
    }
}

class PermafrostTrapEffect extends OneShotEffect<PermafrostTrapEffect> {

    public PermafrostTrapEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Tap up to two target creatures. Those creatures don't untap during their controller's next untap step";
    }

    public PermafrostTrapEffect(final PermafrostTrapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : this.targetPointer.getTargets(game, source)) {
            Permanent creature = game.getPermanent(target);
            if (creature != null) {
                creature.tap(game);
                game.addEffect(new PermafrostEffect2(creature.getId()), source);
            }
        }
        return false;
    }

    @Override
    public PermafrostTrapEffect copy() {
        return new PermafrostTrapEffect(this);
    }
}

class PermafrostEffect2 extends ReplacementEffectImpl<PermafrostEffect2> {

    protected UUID creatureId;

    public PermafrostEffect2(UUID creatureId) {
        super(Constants.Duration.OneUse, Constants.Outcome.Detriment);
        this.creatureId = creatureId;
    }

    public PermafrostEffect2(final PermafrostEffect2 effect) {
        super(effect);
        creatureId = effect.creatureId;
    }

    @Override
    public PermafrostEffect2 copy() {
        return new PermafrostEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        used = true;
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP
                && event.getType() == GameEvent.EventType.UNTAP
                && event.getTargetId().equals(creatureId)) {
            return true;
        }
        return false;
    }
}