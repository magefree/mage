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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DuskmantleSeer extends CardImpl<DuskmantleSeer> {

    public DuskmantleSeer(UUID ownerId) {
        super(ownerId, 159, "Duskmantle Seer", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Vampire");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, each player reveals the top card of his or her library, loses life equal to that card's converted mana cost, then puts it into his or her hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Constants.Zone.BATTLEFIELD, new DuskmantleSeerEffect(), Constants.TargetController.YOU, false, false));

    }

    public DuskmantleSeer(final DuskmantleSeer card) {
        super(card);
    }

    @Override
    public DuskmantleSeer copy() {
        return new DuskmantleSeer(this);
    }
}

class DuskmantleSeerEffect extends OneShotEffect<DuskmantleSeerEffect> {

    public DuskmantleSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of his or her library, loses life equal to that card's converted mana cost, then puts it into his or her hand";
    }

    public DuskmantleSeerEffect(final DuskmantleSeerEffect effect) {
        super(effect);
    }

    @Override
    public DuskmantleSeerEffect copy() {
        return new DuskmantleSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        for (Player player: game.getPlayers().values()) {
            if(player.getLibrary().size() > 0){
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    Cards cards  = new CardsImpl();
                    cards.add(card);
                    player.revealCards(new StringBuilder(sourceCard.getName()).append(": Revealed by ").append(player.getName()).toString(), cards, game);
                    int lifeLost = player.loseLife(card.getManaCost().convertedManaCost(), game);
                    game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(player.getName()).append(" loses ").append(lifeLost).append(" life").toString());
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}
