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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NemesisOfMortals extends CardImpl<NemesisOfMortals> {

    public NemesisOfMortals(UUID ownerId) {
        super(ownerId, 163, "Nemesis of Mortals", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "THS";
        this.subtype.add("Snake");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Nemesis of Mortals costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new NemesisOfMortalsEffect()));

        // {7}{G}{G}: Monstrosity 5.  This ability costs {1} less to activate for each creature card in your graveyard.
        this.addAbility(new MonstrosityAbility("{7}{G}{G}", 5));
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(this.getOwnerId());
        int creatureCount = player.getGraveyard().count(new FilterCreatureCard(), game);
        int cost = 4 - creatureCount;
        String adjustedCost = "{G}{G}";
        if (cost > 0) {
            adjustedCost = "{" + String.valueOf(cost) + "}" + adjustedCost;
        }
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().load(adjustedCost);
    }

    public NemesisOfMortals(final NemesisOfMortals card) {
        super(card);
    }

    @Override
    public NemesisOfMortals copy() {
        return new NemesisOfMortals(this);
    }
}

class NemesisOfMortalsEffect extends OneShotEffect<NemesisOfMortalsEffect> {

    public NemesisOfMortalsEffect() {
        super(Outcome.Neutral);
        this.staticText = "{this} costs {1} less to cast for each creature card in your graveyard";
    }

    public NemesisOfMortalsEffect(final NemesisOfMortalsEffect effect) {
        super(effect);
    }

    @Override
    public NemesisOfMortalsEffect copy() {
        return new NemesisOfMortalsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
