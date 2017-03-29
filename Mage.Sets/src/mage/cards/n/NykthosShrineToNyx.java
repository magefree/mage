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
package mage.cards.n;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class NykthosShrineToNyx extends CardImpl {

    public NykthosShrineToNyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        addSuperType(SuperType.LEGENDARY);

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {T}: Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color.
        Ability ability = new NykthosShrineToNyxManaAbility();
        this.addAbility(ability);
    }

    public NykthosShrineToNyx(final NykthosShrineToNyx card) {
        super(card);
    }

    @Override
    public NykthosShrineToNyx copy() {
        return new NykthosShrineToNyx(this);
    }
}

class NykthosShrineToNyxManaAbility extends ActivatedManaAbilityImpl {

    public NykthosShrineToNyxManaAbility() {
        super(Zone.BATTLEFIELD, new NykthosDynamicManaEffect(), new GenericManaCost(2));
        this.addCost(new TapSourceCost());
    }

    public NykthosShrineToNyxManaAbility(final NykthosShrineToNyxManaAbility ability) {
        super(ability);
    }

    @Override
    public NykthosShrineToNyxManaAbility copy() {
        return new NykthosShrineToNyxManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        netMana.clear();
        if (game != null) {
            for (String colorChoice : ChoiceColor.colorChoices) {
                netMana.add(((NykthosDynamicManaEffect) this.getEffects().get(0)).computeMana(colorChoice, game, this));
            }
        }
        return netMana;
    }
}

class NykthosDynamicManaEffect extends ManaEffect {

    private final Mana computedMana;

    public NykthosDynamicManaEffect() {
        super();
        computedMana = new Mana();
        this.staticText = "Choose a color. Add to your mana pool an amount of mana of that color equal to your devotion to that color. <i>(Your devotion to a color is the number of mana symbols of that color in the mana costs of permanents you control.)</i>";
    }

    public NykthosDynamicManaEffect(final NykthosDynamicManaEffect effect) {
        super(effect);
        this.computedMana = effect.computedMana.copy();
    }

    @Override
    public NykthosDynamicManaEffect copy() {
        return new NykthosDynamicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor choice = new ChoiceColor();
            choice.setMessage("Choose a color for devotion of Nykthos");
            if (controller.choose(outcome, choice, game)) {
                computeMana(choice.getChoice(), game, source);
                checkToFirePossibleEvents(computedMana, game, source);
                controller.getManaPool().addMana(computedMana, game, source);
                return true;
            }
        }
        return false;

    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    public Mana computeMana(String color, Game game, Ability source) {
        this.computedMana.clear();
        if (color != null && !color.isEmpty()) {
            switch (color) {
                case "Red":
                    computedMana.setRed(new DevotionCount(ColoredManaSymbol.R).calculate(game, source, this));
                    break;
                case "Blue":
                    computedMana.setBlue(new DevotionCount(ColoredManaSymbol.U).calculate(game, source, this));
                    break;
                case "White":
                    computedMana.setWhite(new DevotionCount(ColoredManaSymbol.W).calculate(game, source, this));
                    break;
                case "Black":
                    computedMana.setBlack(new DevotionCount(ColoredManaSymbol.B).calculate(game, source, this));
                    break;
                case "Green":
                    computedMana.setGreen(new DevotionCount(ColoredManaSymbol.G).calculate(game, source, this));
                    break;
            }
        }
        return computedMana.copy();
    }
}
