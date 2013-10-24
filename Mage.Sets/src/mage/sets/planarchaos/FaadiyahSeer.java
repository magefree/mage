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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class FaadiyahSeer extends CardImpl<FaadiyahSeer> {

    public FaadiyahSeer(UUID ownerId) {
        super(ownerId, 146, "Fa'adiyah Seer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Draw a card and reveal it. If it isn't a land card, discard it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FaadiyahSeerEffect(), new TapSourceCost()));
    }

    public FaadiyahSeer(final FaadiyahSeer card) {
        super(card);
    }

    @Override
    public FaadiyahSeer copy() {
        return new FaadiyahSeer(this);
    }
}

class FaadiyahSeerEffect extends OneShotEffect<FaadiyahSeerEffect> {

    private static final FilterCard filter = new FilterLandCard();

    public FaadiyahSeerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card and reveal it. If it isn't a land card, discard it";
    }

    public FaadiyahSeerEffect(final FaadiyahSeerEffect effect) {
        super(effect);
    }

    @Override
    public FaadiyahSeerEffect copy() {
        return new FaadiyahSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.drawCards(1, game);
            controller.revealCards("Fa'adiyah Seer", new CardsImpl(card), game);
            if (!filter.match(card, game)) {
                controller.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
