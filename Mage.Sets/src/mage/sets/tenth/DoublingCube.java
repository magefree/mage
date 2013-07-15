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
package mage.sets.tenth;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class DoublingCube extends CardImpl<DoublingCube> {

    public DoublingCube(UUID ownerId) {
        super(ownerId, 321, "Doubling Cube", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "10E";

        // {3}, {tap}: Double the amount of each type of mana in your mana pool.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DoublingCubeEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }

    public DoublingCube(final DoublingCube card) {
        super(card);
    }

    @Override
    public DoublingCube copy() {
        return new DoublingCube(this);
    }
}

class DoublingCubeEffect extends OneShotEffect<DoublingCubeEffect> {

    DoublingCubeEffect() {
        super(Outcome.Benefit);
        staticText = "Double the amount of each type of mana in your mana pool";
    }

    DoublingCubeEffect(final DoublingCubeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        int blackMana = you.getManaPool().getBlack() * 2;
        int whiteMana = you.getManaPool().getWhite() * 2;
        int blueMana = you.getManaPool().getBlue() * 2;
        int greenMana = you.getManaPool().getGreen() * 2;
        int redMana = you.getManaPool().getRed() * 2;
        int colorlessMana = you.getManaPool().getColorless() * 2;
        
        for (int i=0; i<blackMana; i++) {
            you.getManaPool().addMana(Mana.BlackMana, game, source);
        }
        for (int i=0; i<whiteMana; i++) {
            you.getManaPool().addMana(Mana.WhiteMana, game, source);
        }
        for (int i=0; i<blueMana; i++) {
            you.getManaPool().addMana(Mana.BlueMana, game, source);
        }
        for (int i=0; i<greenMana; i++) {
            you.getManaPool().addMana(Mana.GreenMana, game, source);
        }
        for (int i=0; i<redMana; i++) {
            you.getManaPool().addMana(Mana.RedMana, game, source);
        }
        for (int i=0; i<colorlessMana; i++) {
            you.getManaPool().addMana(Mana.ColorlessMana, game, source);
        }
        return true;
    }

    @Override
    public DoublingCubeEffect copy() {
        return new DoublingCubeEffect(this);
    }

 }