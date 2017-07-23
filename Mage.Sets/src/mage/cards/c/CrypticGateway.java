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
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public class CrypticGateway extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    TargetControlledPermanent target;

    public CrypticGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Tap two untapped creatures you control: You may put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrypticGatewayEffect(), new CrypticGatewayCost(new TargetControlledPermanent(filter))));
    }

    public CrypticGateway(final CrypticGateway card) {
        super(card);
    }

    @Override
    public CrypticGateway copy() {
        return new CrypticGateway(this);
    }
}

class CrypticGatewayCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");
    private UUID targetCreatureId = null;
    private UUID targetCreatureId2 = null;

    TargetControlledPermanent target;

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public CrypticGatewayCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Tap two untapped creatures you control";
    }

    public CrypticGatewayCost(final CrypticGatewayCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int numTargets = 0;
        while (numTargets < 2 && target.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId : (List<UUID>) target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(game);
                if (paid) {
                    numTargets++;
                    target.clearChosen();
                }
                for (Effect effect : ability.getEffects()) {
                    if (targetCreatureId == null) {
                        targetCreatureId = permanent.getId();
                    } else if (targetCreatureId2 == null) {
                        targetCreatureId2 = permanent.getId();
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public CrypticGatewayCost copy() {
        return new CrypticGatewayCost(this);
    }

    public UUID getTargetCreatureId() {
        return targetCreatureId;
    }

    public UUID getTargetCreatureId2() {
        return targetCreatureId2;
    }
}

class CrypticGatewayEffect extends OneShotEffect {

    public CrypticGatewayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a creature card from your hand that shares a creature type with each creature tapped this way onto the battlefield";
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
        Player player = game.getPlayer(source.getControllerId());
        if (source == null || source.getCosts() == null) {
            return false;
        }

        FilterCard filter = new FilterCreatureCard("creature card from your hand that shares a creature type with each creature tapped this way");

        for (Cost cost : source.getCosts()) {
            if (cost instanceof CrypticGatewayCost) {
                UUID id = ((CrypticGatewayCost) cost).getTargetCreatureId();
                UUID id2 = ((CrypticGatewayCost) cost).getTargetCreatureId2();
                Permanent creature = game.getPermanentOrLKIBattlefield(id);
                Permanent creature2 = game.getPermanentOrLKIBattlefield(id2);

                if (creature == null || creature2 == null) {
                    return false;
                }

                boolean commonSubType = false;
                boolean changeling = false;
                boolean changeling2 = false;
                if (creature.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature.isAllCreatureTypes()) {
                    changeling = true;
                }
                if (creature2.getAbilities().containsKey(ChangelingAbility.getInstance().getId()) || creature2.isAllCreatureTypes()) {
                    changeling2 = true;
                }

                ArrayList<SubtypePredicate> subtypes = new ArrayList<>();

                for (SubType subtype : creature.getSubtype(game)) {
                    if (creature2.getSubtype(game).contains(subtype) || changeling2) {
                        subtypes.add(new SubtypePredicate(subtype));
                        commonSubType = true;
                    }
                }

                for (SubType subtype : creature2.getSubtype(game)) {
                    if (creature.getSubtype(game).contains(subtype) || changeling) {
                        subtypes.add(new SubtypePredicate(subtype));
                        commonSubType = true;
                    }
                }

                if (changeling && changeling2) {
                    filter = new FilterCreatureCard("creature card from your hand that shares a creature type with each creature tapped this way");
                } else if (commonSubType) {
                    filter.add(Predicates.or(subtypes));
                }

                if (commonSubType) {
                    PutPermanentOnBattlefieldEffect putIntoPlay = new PutPermanentOnBattlefieldEffect(filter);
                    putIntoPlay.apply(game, source);
                }
            }
        }

        return false;
    }
}
