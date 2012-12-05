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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ScribNibblers extends CardImpl<ScribNibblers> {

    public ScribNibblers(UUID ownerId) {
        super(ownerId, 66, "Scrib Nibblers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Rat");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Exile the top card of target player's library. If it's a land card, you gain 1 life.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ScribNibblersEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Landfall - Whenever a land enters the battlefield under your control, you may untap Scrib Nibblers.
        this.addAbility(new LandfallAbility(Constants.Zone.BATTLEFIELD, new UntapSourceEffect(), true));
    }

    public ScribNibblers(final ScribNibblers card) {
        super(card);
    }

    @Override
    public ScribNibblers copy() {
        return new ScribNibblers(this);
    }
}

class ScribNibblersEffect extends OneShotEffect<ScribNibblersEffect> {

    public ScribNibblersEffect() {
        super(Constants.Outcome.Neutral);
        this.staticText = "Exile the top card of target player's library. If it's a land card, you gain 1 life";
    }

    public ScribNibblersEffect(final ScribNibblersEffect effect) {
        super(effect);
    }

    @Override
    public ScribNibblersEffect copy() {
        return new ScribNibblersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null && targetPlayer.getLibrary().size() > 0) {
            Card card = targetPlayer.getLibrary().getFromTop(game);
            card.moveToExile(id, "Scrib Nibblers Exile", source.getId(), game);
            if (card != null && card.getCardType().contains(CardType.LAND)) {
                you.gainLife(1, game);
                return true;
            }
        }
        return false;
    }
}
