/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.PlayerPredicate;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

/*
 * @author emerald000
 */
public class AssistAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterPlayer filter = new FilterPlayer("another player");

    static {
        filter.add(new PlayerPredicate(TargetController.NOT_YOU));
    }

    public AssistAbility() {
        super(Zone.STACK, null);
        this.setRuleAtTheTop(true);
    }

    public AssistAbility(final AssistAbility ability) {
        super(ability);
    }

    @Override
    public AssistAbility copy() {
        return new AssistAbility(this);
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && source.getAbilityType() == AbilityType.SPELL
                && unpaid.getMana().getGeneric() >= 1
                && game.getState().getValue(source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId())) == null) {
            SpecialAction specialAction = new AssistSpecialAction(unpaid);
            specialAction.setControllerId(source.getControllerId());
            specialAction.setSourceId(source.getSourceId());
            Target target = new TargetPlayer(1, 1, true, filter);
            specialAction.addTarget(target);
            if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                game.getState().getSpecialActions().add(specialAction);
            }
        }
    }

    @Override
    public String getRule() {
        return "Assist <i>(Another player can help pay the generic mana of this spell's cost.)</i>";
    }
}

class AssistSpecialAction extends SpecialAction {

    AssistSpecialAction(ManaCost unpaid) {
        super(Zone.ALL, true);
        setRuleVisible(false);
        this.addEffect(new AssistEffect(unpaid));
    }

    AssistSpecialAction(final AssistSpecialAction ability) {
        super(ability);
    }

    @Override
    public AssistSpecialAction copy() {
        return new AssistSpecialAction(this);
    }
}

class AssistEffect extends OneShotEffect {

    private final ManaCost unpaid;

    AssistEffect(ManaCost unpaid) {
        super(Outcome.Benefit);
        this.unpaid = unpaid;
        this.staticText = "Assist <i>(Another player can help pay the generic mana of this spell's cost.)</i>";
    }

    AssistEffect(final AssistEffect effect) {
        super(effect);
        this.unpaid = effect.unpaid;
    }

    @Override
    public AssistEffect copy() {
        return new AssistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            int amountToPay = targetPlayer.announceXMana(0, unpaid.getMana().getGeneric(), "How much mana to pay?", game, source);
            if (amountToPay > 0) {
                Cost cost = new GenericManaCost(amountToPay);
                if (cost.pay(source, game, source.getSourceId(), targetPlayer.getId(), false)) {
                    ManaPool manaPool = controller.getManaPool();
                    manaPool.addMana(Mana.ColorlessMana(amountToPay), game, source);
                    manaPool.unlockManaType(ManaType.COLORLESS);
                    game.informPlayers(targetPlayer.getLogName() + " paid " + amountToPay + " mana.");
                    game.getState().setValue(source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()), true);
                }
            }
            return true;
        }
        return false;
    }
}
