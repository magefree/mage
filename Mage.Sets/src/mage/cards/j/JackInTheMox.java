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
package mage.cards.j;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class JackInTheMox extends CardImpl {

    public JackInTheMox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Roll a six-sided die. This ability has the indicated effect.
        // 1 - Sacrifice Jack-in-the-Mox and you lose 5 life.
        // 2 - Add {W} to your mana pool.
        // 3 - Add {U} to your mana pool.
        // 4 - Add {B} to your mana pool.
        // 5 - Add {R} to your mana pool.
        // 6 - Add {G} to your mana pool.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new JackInTheMoxManaEffect(), new TapSourceCost());
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    public JackInTheMox(final JackInTheMox card) {
        super(card);
    }

    @Override
    public JackInTheMox copy() {
        return new JackInTheMox(this);
    }
}

class JackInTheMoxManaEffect extends ManaEffect {

    JackInTheMoxManaEffect() {
        super();
        staticText = "Roll a six-sided die for {this}. On a 1, sacrifice {this} and lose 5 life. Otherwise, {this} has one of the following effects. Treat this ability as a mana source."
                + "<br/>2 Add {W} to your mana pool.\n"
                + "<br/>3 Add {U} to your mana pool.\n"
                + "<br/>4 Add {B} to your mana pool.\n"
                + "<br/>5 Add {R} to your mana pool.\n"
                + "<br/>6 Add {G} to your mana pool.";
    }

    JackInTheMoxManaEffect(final JackInTheMoxManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            switch (amount) {
                case 1:
                    permanent.sacrifice(source.getSourceId(), game);
                    controller.loseLife(5, game, false);
                    break;
                case 2:
                    controller.getManaPool().addMana(Mana.WhiteMana(1), game, source);
                    break;
                case 3:
                    controller.getManaPool().addMana(Mana.BlueMana(1), game, source);
                    break;
                case 4:
                    controller.getManaPool().addMana(Mana.BlackMana(1), game, source);
                    break;
                case 5:
                    controller.getManaPool().addMana(Mana.RedMana(1), game, source);
                    break;
                case 6:
                    controller.getManaPool().addMana(Mana.GreenMana(1), game, source);
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public JackInTheMoxManaEffect copy() {
        return new JackInTheMoxManaEffect(this);
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }
}
