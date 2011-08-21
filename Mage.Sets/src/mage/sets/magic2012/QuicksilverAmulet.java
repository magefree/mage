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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public class QuicksilverAmulet extends CardImpl<QuicksilverAmulet> {

    public QuicksilverAmulet(UUID ownerId) {
        super(ownerId, 214, "Quicksilver Amulet", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "M12";

        // {4}, {tap}: You may put a creature card from your hand onto the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCreatureOnBattlefieldEffect(),
                new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public QuicksilverAmulet(final QuicksilverAmulet card) {
        super(card);
    }

    @Override
    public QuicksilverAmulet copy() {
        return new QuicksilverAmulet(this);
    }
}

class PutCreatureOnBattlefieldEffect extends OneShotEffect<PutCreatureOnBattlefieldEffect> {

    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    public PutCreatureOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card from your hand onto the battlefield";
    }

    public PutCreatureOnBattlefieldEffect(final PutCreatureOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public PutCreatureOnBattlefieldEffect copy() {
        return new PutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.PutCreatureInPlay, choiceText, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
        target.setRequired(true);
        if (player.choose(Outcome.Benefit, target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.removeFromHand(card, game);
                card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId());
                return true;
            }
        }
        return false;
    }
}
