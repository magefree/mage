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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class Monsoon extends CardImpl {

    public Monsoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}");

        // At the beginning of each player's end step, tap all untapped Islands that player controls and Monsoon deals X damage to the player, where X is the number of Islands tapped this way.
        TriggeredAbility ability = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of each player's end step", true, new MonsoonEffect());
        this.addAbility(ability);
    }

    public Monsoon(final Monsoon card) {
        super(card);
    }

    @Override
    public Monsoon copy() {
        return new Monsoon(this);
    }
}

class MonsoonEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();
    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MonsoonEffect() {
        super(Outcome.Damage);
        this.staticText = "tap all untapped Islands that player controls and {this} deals X damage to the player, where X is the number of Islands tapped this way";
    }

    public MonsoonEffect(Outcome outcome) {
        super(outcome);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player != null) {
            int damage = 0;
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                permanent.tap(game);
                damage++;
            }
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new MonsoonEffect();
    }
}
