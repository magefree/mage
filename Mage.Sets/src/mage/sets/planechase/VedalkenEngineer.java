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
import mage.constants.CardType;
import mage.constants.Rarity;
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
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class VedalkenEngineer extends CardImpl {

    public VedalkenEngineer(UUID ownerId) {
        super(ownerId, 15, "Vedalken Engineer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "HOP";
        this.subtype.add("Vedalken");
        this.subtype.add("Artificer");

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

class VedalkenEngineerAbility extends ManaAbility {

    public VedalkenEngineerAbility(Cost cost, int amount, ConditionalManaBuilder manaBuilder) {
        super(Zone.BATTLEFIELD, new VedalkenEngineerEffect(amount, manaBuilder), cost);
        this.netMana.add(new Mana(0,0,0,0,0,0, amount));
    }

    public VedalkenEngineerAbility(final VedalkenEngineerAbility ability) {
        super(ability);
    }

    @Override
    public VedalkenEngineerAbility copy() {
        return new VedalkenEngineerAbility(this);
    }
}

class VedalkenEngineerEffect extends ManaEffect {

    private final int amount;
    private final ConditionalManaBuilder manaBuilder;

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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Mana mana = new Mana();
        ChoiceColor choiceColor = new ChoiceColor(true);
        while (!controller.choose(Outcome.Benefit, choiceColor, game)) {
            if (!controller.isInGame()) {
                return false;
            }
        }
        if (choiceColor.getColor().isBlack()) {
            mana.setBlack(amount);
        } else if (choiceColor.getColor().isBlue()) {
            mana.setBlue(amount);
        } else if (choiceColor.getColor().isRed()) {
            mana.setRed(amount);
        } else if (choiceColor.getColor().isGreen()) {
            mana.setGreen(amount);
        } else if (choiceColor.getColor().isWhite()) {
            mana.setWhite(amount);
        }
        Mana condMana = manaBuilder.setMana(mana, source, game).build();
        checkToFirePossibleEvents(condMana, game, source);
        controller.getManaPool().addMana(condMana, game, source);
        return true;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

}
