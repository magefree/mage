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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SelvalaExplorerReturned extends CardImpl {

    public SelvalaExplorerReturned(UUID ownerId) {
        super(ownerId, 260, "Selvala, Explorer Returned", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.expansionSetCode = "VMA";
        this.supertype.add("Legendary");
        this.subtype.add("Elf");
        this.subtype.add("Scout");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Parley - {T}: Each player reveals the top card of his or her library. For each nonland card revealed this way, add {G} to your mana pool and you gain 1 life. Then each player draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SelvalaExplorerReturnedEffect(), new TapSourceCost());
        ability.setAbilityWord(AbilityWord.PARLEY);
        Effect effect = new DrawCardAllEffect(1);
        effect.setText("Then each player draws a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public SelvalaExplorerReturned(final SelvalaExplorerReturned card) {
        super(card);
    }

    @Override
    public SelvalaExplorerReturned copy() {
        return new SelvalaExplorerReturned(this);
    }
}

class SelvalaExplorerReturnedEffect extends OneShotEffect {

    public SelvalaExplorerReturnedEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player reveals the top card of his or her library. For each nonland card revealed this way, add {G} to your mana pool and you gain 1 life";
    }

    public SelvalaExplorerReturnedEffect(final SelvalaExplorerReturnedEffect effect) {
        super(effect);
    }

    @Override
    public SelvalaExplorerReturnedEffect copy() {
        return new SelvalaExplorerReturnedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                controller.getManaPool().addMana(new Mana(0, parley, 0, 0, 0, 0, 0), game, source);
                controller.gainLife(parley, game);
            }
            return true;
        }
        return false;
    }
}
