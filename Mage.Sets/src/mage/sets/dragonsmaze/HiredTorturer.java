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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */


public class HiredTorturer extends CardImpl<HiredTorturer> {

    public HiredTorturer (UUID ownerId) {
        super(ownerId, 25, "Hired Torturer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Rogue");
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}{B}, {T}: Target opponent loses 2 life and reveals a card at random from his or her hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(2),new ManaCostsImpl("{3}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new HiredTorturerEffect());
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);

    }

    public HiredTorturer (final HiredTorturer card) {
        super(card);
    }

    @Override
    public HiredTorturer copy() {
        return new HiredTorturer(this);
    }

}

class HiredTorturerEffect extends OneShotEffect<HiredTorturerEffect> {

    public HiredTorturerEffect() {
        super(Outcome.Detriment);
        staticText = "and reveals a card at random from his or her hand";
    }

    public HiredTorturerEffect(final HiredTorturerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && player.getHand().size() > 0) {
            Cards revealed = new CardsImpl();
            revealed.add(player.getHand().getRandom(game));
            player.revealCards("Hired Torturer", revealed, game);
            return true;
        }
        return false;
    }

    @Override
    public HiredTorturerEffect copy() {
        return new HiredTorturerEffect(this);
    }

}
