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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class EtherwroughtPage extends CardImpl<EtherwroughtPage> {

    public EtherwroughtPage(UUID ownerId) {
        super(ownerId, 108, "Etherwrought Page", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}{W}{U}{B}");
        this.expansionSetCode = "ARB";

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // At the beginning of your upkeep, choose one - You gain 2 life; or look at the top card of your library, then you may put that card into your graveyard; or each opponent loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), TargetController.YOU, false);

        // or look at the top card of your library, then you may put that card into your graveyard;
        Mode mode = new Mode();
        mode.getEffects().add(new EtherwroughtPageEffect());
        ability.addMode(mode);

        // or each opponent loses 1 life
        Mode mode1 = new Mode();
        mode1.getEffects().add(new LoseLifeOpponentsEffect(1));
        ability.addMode(mode1);
        
        this.addAbility(ability);

    }

    public EtherwroughtPage(final EtherwroughtPage card) {
        super(card);
    }

    @Override
    public EtherwroughtPage copy() {
        return new EtherwroughtPage(this);
    }
}

class EtherwroughtPageEffect extends OneShotEffect<EtherwroughtPageEffect> {

    public EtherwroughtPageEffect() {
        super(Outcome.DrawCard);
        this.staticText = "or look at the top card of your library, then you may put that card into your graveyard";
    }

    public EtherwroughtPageEffect(final EtherwroughtPageEffect effect) {
        super(effect);
    }

    @Override
    public EtherwroughtPageEffect copy() {
        return new EtherwroughtPageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null && you.getLibrary().size() > 0) {
            Card card = you.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                you.lookAtCards("Etherwrought Page", cards, game);
                if (you.chooseUse(Outcome.Neutral, "Do you wish to put the card into your graveyard?", game)) {
                    return card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
                }
                return true;
            }
        }
        return false;
    }
}