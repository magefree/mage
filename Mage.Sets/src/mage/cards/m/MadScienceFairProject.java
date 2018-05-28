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
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class MadScienceFairProject extends CardImpl {

    public MadScienceFairProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {tap}: Roll a six-sided die. On a 3 or lower, target player adds {C}. Otherwise, that player adds one mana of any color he or she chooses.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MadScienceFairManaEffect(), new TapSourceCost()));
    }

    public MadScienceFairProject(final MadScienceFairProject card) {
        super(card);
    }

    @Override
    public MadScienceFairProject copy() {
        return new MadScienceFairProject(this);
    }
}

class MadScienceFairManaEffect extends ManaEffect {

    public MadScienceFairManaEffect() {
        super();
        this.staticText = "Roll a six-sided die. On a 3 or lower, target player adds {C}. Otherwise, that player adds one mana of any color he or she chooses";
    }

    public MadScienceFairManaEffect(final MadScienceFairManaEffect effect) {
        super(effect);
    }

    @Override
    public MadScienceFairManaEffect copy() {
        return new MadScienceFairManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            return null;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(game, 6);
            if (amount <= 3) {
                return Mana.ColorlessMana(1);
            } else {
                ChoiceColor choice = new ChoiceColor();
                if (controller.choose(Outcome.PutManaInPool, choice, game)) {
                    Mana chosen = choice.getMana(1);
                    checkToFirePossibleEvents(chosen, game, source);
                    return chosen;
                }
            }
        }
        return null;
    }

}
