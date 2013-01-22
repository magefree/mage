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
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author LevelX2
 */
public class WightOfPrecinctSix extends CardImpl<WightOfPrecinctSix> {

    private static final FilterCard filter = new FilterCreatureCard("creature card in your opponents' graveyards");
    static {
        filter.add(new OwnerPredicate(TargetController.OPPONENT));
    }

    public WightOfPrecinctSix(UUID ownerId) {
        super(ownerId, 84, "Wight of Precinct Six", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Wight of Precinct Six gets +1/+1 for each creature card in your opponents' graveyards.
        DynamicValue boost = new CardsInAllGraveyardsCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(boost,boost, Constants.Duration.WhileOnBattlefield)));
    }

    public WightOfPrecinctSix(final WightOfPrecinctSix card) {
        super(card);
    }

    @Override
    public WightOfPrecinctSix copy() {
        return new WightOfPrecinctSix(this);
    }
}

class CardsInAllGraveyardsCount implements DynamicValue {

    private FilterCard filter;

    public CardsInAllGraveyardsCount() {
        this(new FilterCard());
    }

    public CardsInAllGraveyardsCount(FilterCard filter) {
        this.filter = filter;
    }

    private CardsInAllGraveyardsCount(CardsInAllGraveyardsCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        int amount = 0;
        PlayerList playerList = game.getPlayerList();
        for (UUID playerUUID : playerList) {
            Player player = game.getPlayer(playerUUID);
            if (player != null) {
                amount += player.getGraveyard().count(filter, game);
            }
        }
        return amount;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInAllGraveyardsCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
