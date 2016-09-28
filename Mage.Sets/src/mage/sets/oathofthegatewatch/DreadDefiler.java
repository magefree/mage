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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class DreadDefiler extends CardImpl {

    public DreadDefiler(UUID ownerId) {
        super(ownerId, 68, "Dread Defiler", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Eldrazi");
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // {3}{C}, Exile a creature card from your graveyard: Target opponent loses life equal to the exiled card's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DreadDefilerEffect(), new ManaCostsImpl("{3}{C}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"))));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DreadDefiler(final DreadDefiler card) {
        super(card);
    }

    @Override
    public DreadDefiler copy() {
        return new DreadDefiler(this);
    }
}

class DreadDefilerEffect extends OneShotEffect {

    public DreadDefilerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target opponent loses life equal to the exiled card's power";
    }

    public DreadDefilerEffect(final DreadDefilerEffect effect) {
        super(effect);
    }

    @Override
    public DreadDefilerEffect copy() {
        return new DreadDefilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                Card card = game.getCard(cost.getTargets().getFirstTarget());
                if (card != null) {
                    amount = card.getPower().getValue();
                }
                break;
            }
        }
        if (amount > 0) {
            Player targetOpponent = game.getPlayer(source.getFirstTarget());
            if (targetOpponent != null) {
                targetOpponent.loseLife(amount, game, false);
                return true;
            }
        }
        return false;
    }
}
