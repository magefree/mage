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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class SpellRupture extends CardImpl<SpellRupture> {

    public SpellRupture(UUID ownerId) {
        super(ownerId, 52, "Spell Rupture", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);

        // Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new SpellRuptureCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public SpellRupture(final SpellRupture card) {
        super(card);
    }

    @Override
    public SpellRupture copy() {
        return new SpellRupture(this);
    }
}

class SpellRuptureCounterUnlessPaysEffect extends OneShotEffect<SpellRuptureCounterUnlessPaysEffect> {

    public SpellRuptureCounterUnlessPaysEffect() {
        super(Constants.Outcome.Detriment);
    }

    public SpellRuptureCounterUnlessPaysEffect(final SpellRuptureCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SpellRuptureCounterUnlessPaysEffect copy() {
        return new SpellRuptureCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            MageObject sourceObject = game.getObject(source.getSourceId());
            if (player != null && controller != null && sourceObject != null) {
                int amount = new greatestPowerCountCreatureYouControl().calculate(game, source);
                GenericManaCost cost = new GenericManaCost(amount);
                StringBuilder sb = new StringBuilder("Pay {").append(amount).append("}? (otherwise ").append(spell.getName()).append(" will be countered)");
                if (player.chooseUse(Constants.Outcome.Benefit, sb.toString(), game)) {
                    cost.pay(source, game, source.getSourceId(), player.getId(), false);
                }
                if (!cost.isPaid()) {
                    if (game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game)) {
                        game.informPlayers(new StringBuilder(sourceObject.getName()).append(": cost wasn't payed - countering ").append(spell.getName()).toString());
                        return true;
                    }
                }

            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {X}, where X is the greatest power among creatures you control";
    }
}

class greatestPowerCountCreatureYouControl implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int value = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
            if (creature != null && creature.getPower().getValue() > value) {
                value = creature.getPower().getValue();
            }
        }
        return value;
    }

    @Override
    public DynamicValue copy() {
        return new greatestPowerCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "greatest power among creatures you control";
    }
}