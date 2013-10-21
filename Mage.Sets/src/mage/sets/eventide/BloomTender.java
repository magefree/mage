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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class BloomTender extends CardImpl<BloomTender> {

    public BloomTender(UUID ownerId) {
        super(ownerId, 66, "Bloom Tender", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: For each color among permanents you control, add one mana of that color to your mana pool.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BloomTenderEffect(), new TapSourceCost()));

    }

    public BloomTender(final BloomTender card) {
        super(card);
    }

    @Override
    public BloomTender copy() {
        return new BloomTender(this);
    }
}

class BloomTenderEffect extends ManaEffect<BloomTenderEffect> {

    public BloomTenderEffect() {
        super();
        staticText = "For each color among permanents you control, add one mana of that color to your mana pool";
    }

    public BloomTenderEffect(final BloomTenderEffect effect) {
        super(effect);
    }

    @Override
    public BloomTenderEffect copy() {
        return new BloomTenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Mana mana = new Mana();
        if (you != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(you.getId(), game)) {
                if (permanent.getColor().contains(ObjectColor.BLACK)
                        && !mana.contains(Mana.BlackMana)) {
                    mana = Mana.BlackMana(1);
                }
                if (permanent.getColor().contains(ObjectColor.BLUE)
                        && !mana.contains(Mana.BlueMana)) {
                    mana = Mana.BlueMana(1);
                }
                if (permanent.getColor().contains(ObjectColor.RED)
                        && !mana.contains(Mana.RedMana)) {
                    mana = Mana.RedMana(1);
                }
                if (permanent.getColor().contains(ObjectColor.GREEN)
                        && !mana.contains(Mana.GreenMana)) {
                    mana = Mana.GreenMana(1);
                }
                if (permanent.getColor().contains(ObjectColor.WHITE)
                        && !mana.contains(Mana.WhiteMana)) {
                    mana = Mana.WhiteMana(1);
                }
            }
        }
        if (you != null && mana != null) {
            you.getManaPool().addMana(mana, game, source);
            return true;
        }
        return false;
    }
}
