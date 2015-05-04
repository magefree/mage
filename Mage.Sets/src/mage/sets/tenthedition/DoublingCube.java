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
package mage.sets.tenthedition;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.ManaPool;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class DoublingCube extends CardImpl {

    public DoublingCube(UUID ownerId) {
        super(ownerId, 321, "Doubling Cube", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "10E";

        // {3}, {T}: Double the amount of each type of mana in your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new DoublingCubeEffect(), new ManaCostsImpl("{3}"));
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

class DoublingCubeEffect extends ManaEffect {

    DoublingCubeEffect() {
        super();
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
        ManaPool pool = you.getManaPool();
        int blackMana = pool.getBlack();
        int whiteMana = pool.getWhite();
        int blueMana = pool.getBlue();
        int greenMana = pool.getGreen();
        int redMana = pool.getRed();
        int colorlessMana = pool.getColorless();
        Mana mana = new Mana(redMana, greenMana, blueMana, whiteMana, blackMana, colorlessMana, 0);
        checkToFirePossibleEvents(mana, game, source);
        pool.addMana(mana, game, source);
        return true;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }


    @Override
    public DoublingCubeEffect copy() {
        return new DoublingCubeEffect(this);
    }

 }
