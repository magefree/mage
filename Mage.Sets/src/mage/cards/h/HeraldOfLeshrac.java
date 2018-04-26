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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class HeraldOfLeshrac extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("land you control but don't own");
    static {
        filter.add(new OwnerPredicate(TargetController.NOT_YOU));
    }

    public HeraldOfLeshrac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cumulative upkeep-Gain control of a land you don't control.
        this.addAbility(new CumulativeUpkeepAbility(new HeraldOfLeshracCumulativeCost()));

        // Herald of Leshrac gets +1/+1 for each land you control but don't own.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));

        // When Herald of Leshrac leaves the battlefield, each player gains control of each land he or she owns that you control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new HeraldOfLeshracLeavesEffect(), false));
    }

    public HeraldOfLeshrac(final HeraldOfLeshrac card) {
        super(card);
    }

    @Override
    public HeraldOfLeshrac copy() {
        return new HeraldOfLeshrac(this);
    }
}

class HeraldOfLeshracCumulativeCost extends CostImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("land you don't control");
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    HeraldOfLeshracCumulativeCost() {
        this.text = "Gain control of a land you don't control";
    }

    HeraldOfLeshracCumulativeCost(final HeraldOfLeshracCumulativeCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Target target = new TargetPermanent(filter);
        if (target.choose(Outcome.GainControl, controllerId, sourceId, game)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
            game.addEffect(effect, ability);
            game.applyEffects();
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, game, 1);
    }

    @Override
    public HeraldOfLeshracCumulativeCost copy() {
        return new HeraldOfLeshracCumulativeCost(this);
    }
}

class HeraldOfLeshracLeavesEffect extends OneShotEffect {

    HeraldOfLeshracLeavesEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player gains control of each land he or she owns that you control";
    }

    HeraldOfLeshracLeavesEffect(final HeraldOfLeshracLeavesEffect effect) {
        super(effect);
    }

    @Override
    public HeraldOfLeshracLeavesEffect copy() {
        return new HeraldOfLeshracLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (playerId.equals(source.getControllerId())) {
                continue;
            }
            FilterPermanent filter = new FilterLandPermanent();
            filter.add(new OwnerIdPredicate(playerId));
            filter.add(new ControllerIdPredicate(source.getControllerId()));
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, playerId);
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
