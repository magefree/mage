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
package mage.cards.a;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AlignedHedronNetwork extends CardImpl {

    public AlignedHedronNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // When Aligned Hedron Network enters the battlefield, exile all creatures with power 5 or greater until Aligned Hedron Network leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AlignedHedronNetworkExileEffect(), false));
    }

    public AlignedHedronNetwork(final AlignedHedronNetwork card) {
        super(card);
    }

    @Override
    public AlignedHedronNetwork copy() {
        return new AlignedHedronNetwork(this);
    }
}

class AlignedHedronNetworkExileEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public AlignedHedronNetworkExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all creatures with power 5 or greater until {this} leaves the battlefield";
    }

    public AlignedHedronNetworkExileEffect(final AlignedHedronNetworkExileEffect effect) {
        super(effect);
    }

    @Override
    public AlignedHedronNetworkExileEffect copy() {
        return new AlignedHedronNetworkExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Whale leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (controller != null && permanent != null) {
            Set<Card> toExile = new LinkedHashSet<>();
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, controller.getId(), source.getSourceId(), game)) {
                toExile.add(creature);
            }
            if (!toExile.isEmpty()) {
                controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
                new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
