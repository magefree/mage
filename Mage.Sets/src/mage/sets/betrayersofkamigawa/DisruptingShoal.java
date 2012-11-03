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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class DisruptingShoal extends CardImpl<DisruptingShoal> {

    private static final String ALTERNATIVE_COST_DESCRIPTION = "You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost";
    private static final FilterCard filter = new FilterCard("blue card from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(new OwnerPredicate(Constants.TargetController.YOU));
    }

    public DisruptingShoal(UUID ownerId) {
        super(ownerId, 33, "Disrupting Shoal", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{U}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");
        this.color.setBlue(true);

        // You may exile a blue card with converted mana cost X from your hand rather than pay Disrupting Shoal's mana cost.
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl(ALTERNATIVE_COST_DESCRIPTION, new ExileFromHandCost(new TargetCardInHand(filter))));

        // 2/1/2005: Disrupting Shoal can target any spell, but does nothing unless that spell's converted mana cost is X.
        // Counter target spell if its converted mana cost is X.
        this.getSpellAbility().addEffect(new DisruptingShoalCounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public DisruptingShoal(final DisruptingShoal card) {
        super(card);
    }

    @Override
    public DisruptingShoal copy() {
        return new DisruptingShoal(this);
    }
}
// ConvertedManaExileFromHandOrVariableManaValue
class DisruptingShoalVariableValue implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        for (AlternativeCost aCost: (List<AlternativeCost>) sourceAbility.getAlternativeCosts()) {
            if (aCost.isPaid()) {
                for (int x=0; x < ((Costs) aCost).size(); x++) {
                    Cost cost = (Cost) ((Costs) aCost).get(x);
                    if (cost instanceof ExileFromHandCost) {
                        int xValue = 0;
                        for (Card card : ((ExileFromHandCost) cost).getCards()) {
                            xValue += card.getManaCost().convertedManaCost();
                        }
                        return xValue;
                    }
                }
            }
        }
        return sourceAbility.getManaCostsToPay().getX();
    }

    @Override
    public DynamicValue clone() {
        return new DisruptingShoalVariableValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class DisruptingShoalCounterTargetEffect extends OneShotEffect<DisruptingShoalCounterTargetEffect> {

    public DisruptingShoalCounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public DisruptingShoalCounterTargetEffect(final DisruptingShoalCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public DisruptingShoalCounterTargetEffect copy() {
        return new DisruptingShoalCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue amount = new DisruptingShoalVariableValue();
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null && spell.getManaCost().convertedManaCost() == amount.calculate(game, source)) {
            return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell if its converted mana cost is X";
    }

}
