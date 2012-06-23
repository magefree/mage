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
package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerTurnActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX
 */
public class FeralDeceiver extends CardImpl<FeralDeceiver> {

    public FeralDeceiver(UUID ownerId) {
        super(ownerId, 208, "Feral Deceiver", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Spirit");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}: Look at the top card of your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(), new GenericManaCost(1)));  

        // {2}: Reveal the top card of your library. If it's a land card, {this} gets +2/+2 and gains trample until end of turn.
        Ability ability = new FeralDeceiverAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2,Duration.EndOfTurn), new ManaCostsImpl("{2}"));
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(),Duration.EndOfTurn));
        this.addAbility(ability);
    }

    public FeralDeceiver(final FeralDeceiver card) {
        super(card);
    }

    @Override
    public FeralDeceiver copy() {
        return new FeralDeceiver(this);
    }
}

class FeralDeceiverAbility extends ActivateOncePerTurnActivatedAbility {

        public FeralDeceiverAbility(Zone zone, Effect effect, Cost cost) {
        super(zone, effect, cost);
    }

        public FeralDeceiverAbility(FeralDeceiverAbility ability) {
        super(ability);
    }

        @Override
        public FeralDeceiverAbility copy() {
                return new FeralDeceiverAbility(this);
        }

        @Override
    public boolean checkIfClause(Game game) {
                Player player = game.getPlayer(this.getControllerId());
                if (player != null) {
                    Cards cards = new CardsImpl();
                    Card card = player.getLibrary().getFromTop(game);
                    cards.add(card);
                    player.revealCards("Feral Deceiver", cards, game);
                    if (card != null && card.getCardType().contains(CardType.LAND)) {
                            return true;
                    }
                }
        return false;
        }

        @Override
    public String getRule() {
        return "{2}: Reveal the top card of your library. If it's a land card, {this} gets +2/+2 and gains trample until end of turn. Activate this ability only once each turn."; 
    }
}