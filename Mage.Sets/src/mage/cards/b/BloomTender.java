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
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
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
public class BloomTender extends CardImpl {

    public BloomTender(UUID ownerId) {
        super(ownerId, 66, "Bloom Tender", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elf");
        this.subtype.add("Druid");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: For each color among permanents you control, add one mana of that color to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BloomTenderEffect(), new TapSourceCost()));

    }

    public BloomTender(final BloomTender card) {
        super(card);
    }

    @Override
    public BloomTender copy() {
        return new BloomTender(this);
    }
}

class BloomTenderEffect extends ManaEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = getMana(game, source);
            checkToFirePossibleEvents(mana, game, source);
            controller.getManaPool().addMana(mana, game, source);            
            return true;
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Mana mana = new Mana();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                if (mana.getBlack() == 0 && permanent.getColor(game).isBlack()) {
                    mana.increaseBlack();
                }
                if (mana.getBlue() == 0 && permanent.getColor(game).isBlue()) {
                    mana.increaseBlue();
                }
                if (mana.getRed() == 0 && permanent.getColor(game).isRed()) {
                    mana.increaseRed();
                }
                if (mana.getGreen() == 0 && permanent.getColor(game).isGreen()) {
                    mana.increaseGreen();
                }
                if (mana.getWhite() == 0 && permanent.getColor(game).isWhite()) {
                    mana.increaseWhite();
                }
            }
            return mana;
        }
        return null;
    }


}
