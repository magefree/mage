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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author noxx
 */
public class NecromancersStockpile extends CardImpl {

    private final FilterCard filter = new FilterCreatureCard();

    public NecromancersStockpile(UUID ownerId) {
        super(ownerId, 108, "Necromancer's Stockpile", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "M15";

        // {1}{B}, Discard a creature card: Draw a card.
        // If the discarded card was a Zombie card, put a 2/2 black Zombie creature token onto the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new NecromancersStockpileDiscardTargetCost(new TargetCardInHand(filter)));
        ability.addEffect(new NecromancersStockpilePutTokenEffect());
        this.addAbility(ability);
    }

    public NecromancersStockpile(final NecromancersStockpile card) {
        super(card);
    }

    @Override
    public NecromancersStockpile copy() {
        return new NecromancersStockpile(this);
    }

}

class NecromancersStockpileDiscardTargetCost extends CostImpl {

    protected boolean isZombieCard;

    public NecromancersStockpileDiscardTargetCost(TargetCardInHand target) {
        this.addTarget(target);
        this.text = "Discard " + target.getTargetName();
    }

    public NecromancersStockpileDiscardTargetCost(NecromancersStockpileDiscardTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Discard, controllerId, sourceId, game)) {
            Player player = game.getPlayer(controllerId);
            for (UUID targetId : targets.get(0).getTargets()) {
                Card card = player.getHand().get(targetId, game);
                if (card == null) {
                    return false;
                }
                isZombieCard = card.getSubtype().contains("Zombie");
                paid |= player.discard(card, null, game);

            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public NecromancersStockpileDiscardTargetCost copy() {
        return new NecromancersStockpileDiscardTargetCost(this);
    }

    public boolean isZombieCard() {
        return isZombieCard;
    }

}

class NecromancersStockpilePutTokenEffect extends OneShotEffect {

    NecromancersStockpilePutTokenEffect() {
        super(Outcome.Neutral);
        staticText = "If the discarded card was a Zombie card, put a 2/2 black Zombie creature token onto the battlefield tapped";
    }

    NecromancersStockpilePutTokenEffect(final NecromancersStockpilePutTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NecromancersStockpileDiscardTargetCost cost = (NecromancersStockpileDiscardTargetCost) source.getCosts().get(0);
        if (cost != null && cost.isZombieCard()) {
            new CreateTokenEffect(new ZombieToken(), 1, true, false).apply(game, source);
        }
        return true;
    }

    @Override
    public NecromancersStockpilePutTokenEffect copy() {
        return new NecromancersStockpilePutTokenEffect(this);
    }
}
