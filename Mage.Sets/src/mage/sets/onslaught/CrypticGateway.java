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
package mage.sets.onslaught;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author anonymous
 */
public class CrypticGateway extends CardImpl {

    static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("untapped");

    public CrypticGateway(UUID ownerId) {
        super(ownerId, 306, "Cryptic Gateway", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "ONS";

        // Tap two untapped creatures you control: You may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrypticGatewayEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter1, true))));

    }

    public CrypticGateway(final CrypticGateway card) {
        super(card);
    }

    @Override
    public CrypticGateway copy() {
        return new CrypticGateway(this);
    }
}

class CrypticGatewayEffect extends OneShotEffect {

    public CrypticGatewayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You may put a creature card with converted mana cost equal to the number of charge counters on {this} from your hand onto the battlefield";
    }

    public CrypticGatewayEffect(final CrypticGatewayEffect effect) {
        super(effect);
    }

    @Override
    public CrypticGatewayEffect copy() {
        return new CrypticGatewayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        FilterCreatureCard filter = new FilterCreatureCard("creature card");

        for (Target target : (ArrayList<Target>) source.getTargets()) {
            for (UUID targetId : (List<UUID>) target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                for (String subtype : (List<String>) permanent.getSubtype()) {
                    filter.add(new SubtypePredicate(subtype));
                }
                
            }
        }
        
        String choiceText = "Put a " + filter.getMessage() + " from your hand onto the battlefield?";

        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(filter, game) == 0
                || !player.chooseUse(this.outcome, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(this.outcome, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                return true;
            }
        }
        return false;
    }
}
