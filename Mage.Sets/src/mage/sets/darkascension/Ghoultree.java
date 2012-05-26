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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class Ghoultree extends CardImpl<Ghoultree> {

    public Ghoultree(UUID ownerId) {
        super(ownerId, 115, "Ghoultree", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{7}{G}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Zombie");
        this.subtype.add("Treefolk");

        this.color.setGreen(true);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Ghoultree costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new GhoultreeEffect()));
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(this.getOwnerId());
        int creatureCount = player.getGraveyard().count(new FilterCreatureCard(), game);
        int cost = 7 - creatureCount;
        String adjustedCost = "{G}";
        if (cost > 0) {
            adjustedCost = "{" + String.valueOf(cost) + "}" + adjustedCost;
        }
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().load(adjustedCost);
    }

    public Ghoultree(final Ghoultree card) {
        super(card);
    }

    @Override
    public Ghoultree copy() {
        return new Ghoultree(this);
    }
}

class GhoultreeEffect extends OneShotEffect<GhoultreeEffect> {

    public GhoultreeEffect() {
        super(Outcome.Neutral);
        this.staticText = "{this} costs {1} less to cast for each creature card in your graveyard";
    }

    public GhoultreeEffect(final GhoultreeEffect effect) {
        super(effect);
    }

    @Override
    public GhoultreeEffect copy() {
        return new GhoultreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
