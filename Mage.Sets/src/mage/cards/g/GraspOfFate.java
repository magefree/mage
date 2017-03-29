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
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

import static mage.filter.predicate.permanent.ControllerControlsIslandPredicate.filter;

/**
 *
 * @author fireshoes
 */
public class GraspOfFate extends CardImpl {

    public GraspOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // When Grasp of Fate enters the battlefield, for each opponent, exile up to one target nonland permanent that player controls until Grasp of Fate leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GraspOfFateExileEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    public GraspOfFate(final GraspOfFate card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("nonland permanent from opponent " + opponent.getLogName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
                    TargetPermanent target = new TargetPermanent(0, 1, filter, false);
                    ability.addTarget(target);
                }
            }
        }
    }

    @Override
    public GraspOfFate copy() {
        return new GraspOfFate(this);
    }
}

class GraspOfFateExileEffect extends OneShotEffect {

    public GraspOfFateExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile up to one target nonland permanent that player controls until {this} leaves the battlefield";
    }

    public GraspOfFateExileEffect(final GraspOfFateExileEffect effect) {
        super(effect);
    }

    @Override
    public GraspOfFateExileEffect copy() {
        return new GraspOfFateExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return new ConditionalOneShotEffect(new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName(), Zone.BATTLEFIELD, true), new SourceOnBattlefieldCondition()).apply(game, source);
        }
        return false;
    }
}
