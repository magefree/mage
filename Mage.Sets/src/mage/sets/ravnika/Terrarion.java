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
package mage.sets.ravnika;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Terrarion extends CardImpl<Terrarion> {

    public Terrarion(UUID ownerId) {
        super(ownerId, 273, "Terrarion", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "RAV";

        // Terrarion enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {2}, {tap}, Sacrifice Terrarion: Add two mana in any combination of colors to your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new TerrarionManaEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addChoice(new ChoiceColor());
        ability.addChoice(new ChoiceColor());
        this.addAbility(ability);
        // When Terrarion is put into a graveyard from the battlefield, draw a card.
        this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new DrawCardControllerEffect(1)));
    }

    public Terrarion(final Terrarion card) {
        super(card);
    }

    @Override
    public Terrarion copy() {
        return new Terrarion(this);
    }
}

class TerrarionManaEffect extends ManaEffect<TerrarionManaEffect> {

    public TerrarionManaEffect() {
        super();
        this.staticText = "Add two mana in any combination of colors to your mana pool";
    }

    public TerrarionManaEffect(final TerrarionManaEffect effect) {
        super(effect);
    }

    @Override
    public TerrarionManaEffect copy() {
        return new TerrarionManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean result = false;
        for (int i = 0; i < 2; i++) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(i);
            Mana mana = null;
            if (choice.getColor().isBlack()) {
                mana = Mana.BlackMana(1);
            } else if (choice.getColor().isBlue()) {
                mana = Mana.BlueMana(1);
            } else if (choice.getColor().isRed()) {
                mana = Mana.RedMana(1);
            } else if (choice.getColor().isGreen()) {
                mana = Mana.GreenMana(1);
            } else if (choice.getColor().isWhite()) {
                mana = Mana.WhiteMana(1);
            }

            if (mana != null) {
                player.getManaPool().addMana(mana, game, source);
                result = true;
            }
        }
        return result;
    }
}
