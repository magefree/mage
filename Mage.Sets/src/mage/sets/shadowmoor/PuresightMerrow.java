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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public class PuresightMerrow extends CardImpl {

    public PuresightMerrow(UUID ownerId) {
        super(ownerId, 146, "Puresight Merrow", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W/U}{W/U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {WU}, {untap}: Look at the top card of your library. You may exile that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PuresightMerrowEffect(), new ManaCostsImpl("{W/U}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);

    }

    public PuresightMerrow(final PuresightMerrow card) {
        super(card);
    }

    @Override
    public PuresightMerrow copy() {
        return new PuresightMerrow(this);
    }
}

class PuresightMerrowEffect extends OneShotEffect {

    public PuresightMerrowEffect() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of your library. You may exile that card";
    }

    public PuresightMerrowEffect(final PuresightMerrowEffect effect) {
        super(effect);
    }

    @Override
    public PuresightMerrowEffect copy() {
        return new PuresightMerrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards("Puresight Merrow", cards, game);
                if (controller.chooseUse(Outcome.Removal, "Do you wish to exile the card from the top of your library?", game)) {
                    controller.moveCardToExileWithInfo(card, source.getSourceId(), "Puresight Merrow", source.getSourceId(), game, Zone.LIBRARY, true);
                } else {
                    game.informPlayers(controller.getName() + " puts the card back on top of their library.");
                }
                return true;
            }
        }
        return false;
    }

}
