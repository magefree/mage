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
package mage.abilities.effects.common.cost;

import java.util.LinkedHashSet;
import java.util.Set;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.choices.ChoiceImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author North
 */
public class SpellsCostReductionControllerEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final int amount;
    private final boolean upTo;
    private ManaCosts<ManaCost> manaCostsToReduce = null;

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = 0;
        this.manaCostsToReduce = manaCostsToReduce;
        this.upTo = false;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost ");
        for (String manaSymbol : manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" less to cast. This effect reduces only the amount of colored mana you pay.");
        this.staticText = sb.toString();
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount, boolean upTo) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.upTo = upTo;
        this.staticText = filter.getMessage() + " you cast cost " + (upTo ? "up to " : "") + "{" + amount + "} less to cast";
    }

    protected SpellsCostReductionControllerEffect(final SpellsCostReductionControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.upTo = effect.upTo;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToReduce != null) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce, false);
        } else {
            if (upTo) {
                Mana mana = abilityToModify.getManaCostsToPay().getMana();
                int reduceMax = mana.getColorless();
                if (reduceMax > 2) {
                    reduceMax = 2;
                }
                if (reduceMax > 0) {
                    Player controller = game.getPlayer(abilityToModify.getControllerId());
                    if (controller == null) {
                        return false;
                    }
                    ChoiceImpl choice = new ChoiceImpl(true);
                    Set<String> set = new LinkedHashSet<>();
                    for (int i = 0; i <= reduceMax; i++) {
                        set.add(String.valueOf(i));
                    }
                    choice.setChoices(set);
                    choice.setMessage("Reduce cost of " + filter);
                    if (controller.choose(Outcome.Benefit, choice, game)) {
                        int reduce = Integer.parseInt(choice.getChoice());
                        CardUtil.reduceCost(abilityToModify, reduce);
                    }
                }
            } else {
                CardUtil.reduceCost(abilityToModify, this.amount);
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return this.filter.match(spell, source.getSourceId(), source.getControllerId(), game);
                } else {
                    // used at least for flashback ability because Flashback ability doesn't use stack or for getPlayables where spell is not cast yet
                    Card sourceCard = game.getCard(abilityToModify.getSourceId());
                    return sourceCard != null && this.filter.match(sourceCard, source.getSourceId(), source.getControllerId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public SpellsCostReductionControllerEffect copy() {
        return new SpellsCostReductionControllerEffect(this);
    }
}
