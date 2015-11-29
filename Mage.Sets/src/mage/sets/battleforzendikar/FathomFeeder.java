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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class FathomFeeder extends CardImpl {

    public FathomFeeder(UUID ownerId) {
        super(ownerId, 203, "Fathom Feeder", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.expansionSetCode = "BFZ";
        this.subtype.add("Eldrazi");
        this.subtype.add("Drone");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ingest
        this.addAbility(new IngestAbility());

        // {3}{U}{B}: Draw a card. Each opponent exiles the top card of his or her library.
        Effect effect = new FathomFeederEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{3}{U}{B}"));
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public FathomFeeder(final FathomFeeder card) {
        super(card);
    }

    @Override
    public FathomFeeder copy() {
        return new FathomFeeder(this);
    }
}

class FathomFeederEffect extends OneShotEffect {
    public FathomFeederEffect() {
        super(Outcome.Exile);
        this.staticText = "Each opponent exiles the top card of his or her library";
    }

    public FathomFeederEffect(final FathomFeederEffect effect) {
        super(effect);
    }

    @Override
    public FathomFeederEffect copy() {
        return new FathomFeederEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    player.moveCards(card, Zone.LIBRARY, Zone.EXILED, source, game);
                }
            }
        }
        return true;
    }
}
