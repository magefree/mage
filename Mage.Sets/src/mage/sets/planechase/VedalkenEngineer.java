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
package mage.sets.planechase;

import java.util.UUID;
import mage.ConditionalMana;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class VedalkenEngineer extends CardImpl<VedalkenEngineer> {

    public VedalkenEngineer(UUID ownerId) {
        super(ownerId, 15, "Vedalken Engineer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Vedalken");
        this.subtype.add("Artificer");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add two mana of any one color to your mana pool. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new VedalkenEngineerAbility(new TapSourceCost(), 2, new VedalkenEngineerManaBuilder()));
    }

    public VedalkenEngineer(final VedalkenEngineer card) {
        super(card);
    }

    @Override
    public VedalkenEngineer copy() {
        return new VedalkenEngineer(this);
    }
}

class VedalkenEngineerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new VedalkenEngineerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class VedalkenEngineerConditionalMana extends ConditionalMana {

    public VedalkenEngineerConditionalMana(Mana mana) {
        super(mana);
        addCondition(new VedalkenEngineerManaCondition());
    }
}

class VedalkenEngineerManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.getCardType().contains(CardType.ARTIFACT)) {
            return true;
        }
        return false;
    }
}

class VedalkenEngineerAbility extends ManaAbility<VedalkenEngineerAbility> {

    public VedalkenEngineerAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        super(Constants.Zone.BATTLEFIELD, new VedalkenEngineerEffect(amount, manaBuilder), cost);
        this.addChoice(new ChoiceColor());
        this.netMana.setAny(amount);
    }

    public VedalkenEngineerAbility(final VedalkenEngineerAbility ability) {
        super(ability);
    }

    @Override
    public VedalkenEngineerAbility copy() {
        return new VedalkenEngineerAbility(this);
    }
}

class VedalkenEngineerEffect extends ManaEffect<VedalkenEngineerEffect> {

    private int amount;
    private ConditionalManaBuilder manaBuilder;

    public VedalkenEngineerEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        staticText = "Add " + amount + " mana of any one color to your mana pool. " + manaBuilder.getRule();
    }

    public VedalkenEngineerEffect(final VedalkenEngineerEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public VedalkenEngineerEffect copy() {
        return new VedalkenEngineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean result = false;
        for (int i = 0; i < amount; i++) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            Mana mana = null;
            if (choice.getColor().isBlack()) {
                mana = manaBuilder.setMana(Mana.BlackMana(1)).build();
            } else if (choice.getColor().isBlue()) {
                mana = manaBuilder.setMana(Mana.BlueMana(1)).build();
            } else if (choice.getColor().isRed()) {
                mana = manaBuilder.setMana(Mana.RedMana(1)).build();
            } else if (choice.getColor().isGreen()) {
                mana = manaBuilder.setMana(Mana.GreenMana(1)).build();
            } else if (choice.getColor().isWhite()) {
                mana = manaBuilder.setMana(Mana.WhiteMana(1)).build();
            }
            if (mana != null) {
                player.getManaPool().addMana(mana, game, source);
                result = true;
            }
        }
        return result;
    }
}