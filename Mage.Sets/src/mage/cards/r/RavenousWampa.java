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
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import static mage.cards.r.RavenousWampa.RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX;

/**
 *
 * @author Styxo
 */
public class RavenousWampa extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    static final String RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX = "TOU_SAC_CRE";

    public RavenousWampa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}");
        this.subtype.add("Beast");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{G}, Sacrifice another creature: Monstrosity 2.
        Ability ability = new MonstrosityAbility("{2}{G}", 2);
        ability.addCost(new RavenousWampaSacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);

        // When Ravenous Wampa becomes monstrous, you gain life equal to the sacrificied creature's toughness.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(new RavenousWampaEffect()));
    }

    public RavenousWampa(final RavenousWampa card) {
        super(card);
    }

    @Override
    public RavenousWampa copy() {
        return new RavenousWampa(this);
    }
}

class RavenousWampaEffect extends OneShotEffect {

    public RavenousWampaEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain life equal to the sacrificied creature's toughness";
    }

    public RavenousWampaEffect(final RavenousWampaEffect effect) {
        super(effect);
    }

    @Override
    public RavenousWampaEffect copy() {
        return new RavenousWampaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Integer toughness = (Integer) game.getState().getValue(RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX + source.getSourceId() + sourceObject.getZoneChangeCounter(game));
            if (toughness != null) {
                controller.gainLife(toughness, game);
            }
            return true;
        }
        return false;
    }
}

class RavenousWampaSacrificeTargetCost extends SacrificeTargetCost {

    public RavenousWampaSacrificeTargetCost(TargetControlledPermanent target) {
        super(target);
    }

    public RavenousWampaSacrificeTargetCost(RavenousWampaSacrificeTargetCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        boolean result = super.pay(ability, game, sourceId, controllerId, noMana, costToPay);
        if (paid && !getPermanents().isEmpty()) {
            Permanent sacrificedPermanen = getPermanents().get(0);
            Permanent sourcePermanent = game.getPermanent(sourceId);
            if (sourcePermanent != null && sacrificedPermanen != null) {
                game.getState().setValue(RAVENOUS_WAMPA_STATE_VALUE_KEY_PREFIX + sourceId + sourcePermanent.getZoneChangeCounter(game), sacrificedPermanen.getToughness().getValue());
            }
        }
        return result;
    }

    @Override
    public RavenousWampaSacrificeTargetCost copy() {
        return new RavenousWampaSacrificeTargetCost(this);
    }

}
