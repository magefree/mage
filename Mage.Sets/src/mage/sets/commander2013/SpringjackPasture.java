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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class SpringjackPasture extends CardImpl<SpringjackPasture> {

    public SpringjackPasture(UUID ownerId) {
        super(ownerId, 326, "Springjack Pasture", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C13";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {tap}: Put a 0/1 white Goat creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoatToken()), new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {tap}, Sacrifice X Goats: Add X mana of any one color to your mana pool. You gain X life.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpringjackPastureEffect(), new TapSourceCost());
        ability2.addCost(new SpringjackPastureCost());
        this.addAbility(ability2);

    }

    public SpringjackPasture(final SpringjackPasture card) {
        super(card);
    }

    @Override
    public SpringjackPasture copy() {
        return new SpringjackPasture(this);
    }
}

class SpringjackPastureEffect extends OneShotEffect<SpringjackPastureEffect> {

    public SpringjackPastureEffect() {
        super(Outcome.Benefit);
        staticText = "Add X mana of any one color to your mana pool. You gain X life";
    }

    public SpringjackPastureEffect(final SpringjackPastureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
        int count = new GetXValue().calculate(game, source);
        if (choice.getColor().isBlack()) {
            you.getManaPool().addMana(new Mana(0, 0, 0, 0, count, 0, 0), game, source);
            you.gainLife(count, game);
            return true;
        } else if (choice.getColor().isBlue()) {
            you.getManaPool().addMana(new Mana(0, 0, count, 0, 0, 0, 0), game, source);
            you.gainLife(count, game);
            return true;
        } else if (choice.getColor().isRed()) {
            you.getManaPool().addMana(new Mana(count, 0, 0, 0, 0, 0, 0), game, source);
            you.gainLife(count, game);
            return true;
        } else if (choice.getColor().isGreen()) {
            you.getManaPool().addMana(new Mana(0, count, 0, 0, 0, 0, 0), game, source);
            you.gainLife(count, game);
            return true;
        } else if (choice.getColor().isWhite()) {
            you.getManaPool().addMana(new Mana(0, 0, 0, count, 0, 0, 0), game, source);
            you.gainLife(count, game);
            return true;
        }
        return false;
    }

    @Override
    public SpringjackPastureEffect copy() {
        return new SpringjackPastureEffect(this);
    }
}

class SpringjackPastureCost extends CostImpl<SpringjackPastureCost> implements VariableCost {

    protected int amountPaid = 0;

    public SpringjackPastureCost() {
        this.text = "sacrifice X Goats";
    }

    public SpringjackPastureCost(final SpringjackPastureCost cost) {
        super(cost);
        this.amountPaid = cost.amountPaid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        amountPaid = 0;
        FilterCreaturePermanent filter = new FilterCreaturePermanent("X number of goats you control.");
        filter.add(new SubtypePredicate("Goat"));
        filter.add(new ControllerPredicate(TargetController.YOU));
        TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
        while (true) {
            target.clearChosen();
            if (target.canChoose(controllerId, game) && target.choose(Outcome.Sacrifice, controllerId, sourceId, game)) {
                UUID goat = target.getFirstTarget();
                if (goat != null) {
                    game.getPermanent(goat).sacrifice(sourceId, game);
                    amountPaid++;
                }
            } else {
                break;
            }
        }
        paid = true;
        return true;
    }

    @Override
    public int getAmount() {
        return amountPaid;
    }

    @Override
    public void setFilter(FilterMana filter) {
    }

    @Override
    public FilterMana getFilter() {
        return new FilterMana();
    }

    @Override
    public SpringjackPastureCost copy() {
        return new SpringjackPastureCost(this);
    }

    @Override
    public void setAmount(int amount) {
        amountPaid = amount;
    }
}

class GoatToken extends Token {

    public GoatToken() {
        super("Goat", "a 0/1 white Goat creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Goat");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}