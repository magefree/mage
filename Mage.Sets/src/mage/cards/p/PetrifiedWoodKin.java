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
package mage.cards.p;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.DamageDoneWatcher;

/**
 *
 * @author TheElk801
 */
public final class PetrifiedWoodKin extends CardImpl {

    private static final FilterCard filter = new FilterCard("instants");

    static {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public PetrifiedWoodKin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Petrified Wood-Kin can't be countered.
        this.addAbility(new CantBeCounteredAbility());

        // Bloodthirst X
        this.addAbility(new EntersBattlefieldAbility(
                new PetrifiedWoodKinEffect(),
                "Bloodthirst X "
                + "<i>(This creature enters the battlefield with X +1/+1 counters on it, "
                + "where X is the damage dealt to your opponents this turn.)</i>"
        ), new DamageDoneWatcher());

        // Protection from instants
        this.addAbility(new ProtectionAbility(filter));
    }

    public PetrifiedWoodKin(final PetrifiedWoodKin card) {
        super(card);
    }

    @Override
    public PetrifiedWoodKin copy() {
        return new PetrifiedWoodKin(this);
    }
}

class PetrifiedWoodKinEffect extends OneShotEffect {

    PetrifiedWoodKinEffect() {
        super(Outcome.BoostCreature);
        staticText = "";
    }

    PetrifiedWoodKinEffect(final PetrifiedWoodKinEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        DamageDoneWatcher watcher = (DamageDoneWatcher) game.getState().getWatchers().get(DamageDoneWatcher.class.getSimpleName());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || watcher == null || permanent == null) {
            return false;
        }
        ArrayList<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects"); // the basic event is the EntersBattlefieldEvent, so use already applied replacement effects from that event
        int amount = 0;
        for (UUID opponentId : game.getOpponents(player.getId())) {
            MageObjectReference mor = new MageObjectReference(opponentId, game);
            amount += watcher.damagedObjects.getOrDefault(mor, 0);
        }
        permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game, appliedEffects);
        return true;
    }

    @Override
    public PetrifiedWoodKinEffect copy() {
        return new PetrifiedWoodKinEffect(this);
    }
}
