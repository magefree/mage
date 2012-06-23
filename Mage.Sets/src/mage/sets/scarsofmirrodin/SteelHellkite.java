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
package mage.sets.scarsofmirrodin;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.WatcherImpl;

import java.util.*;
import mage.Constants.WatcherScope;
import mage.filter.common.FilterNonlandPermanent;

/**
 * @author nantuko
 */
public class SteelHellkite extends CardImpl<SteelHellkite> {

    public SteelHellkite(UUID ownerId) {
        super(ownerId, 205, "Steel Hellkite", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Dragon");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // {2}: Steel Hellkite gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Constants.Duration.EndOfTurn), new GenericManaCost(2)));
        // {X}: Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn. Activate this ability only once each turn.
        this.addAbility(new ActivateOncePerTurnActivatedAbility(Constants.Zone.BATTLEFIELD, new SteelHellkiteDestroyEffect(), new ManaCostsImpl("{X}")));

        this.addWatcher(new SteelHellkiteWatcher());
    }

    public SteelHellkite(final SteelHellkite card) {
        super(card);
    }

    @Override
    public SteelHellkite copy() {
        return new SteelHellkite(this);
    }
}

class SteelHellkiteDestroyEffect extends OneShotEffect {

    public SteelHellkiteDestroyEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn";
    }

    public SteelHellkiteDestroyEffect(final SteelHellkiteDestroyEffect effect) {
        super(effect);
    }

    @Override
    public SteelHellkiteDestroyEffect copy() {
        return new SteelHellkiteDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SteelHellkiteWatcher watcher = (SteelHellkiteWatcher) game.getState().getWatchers().get("SteelHellkiteWatcher", source.getSourceId());
        if (watcher != null) {
            int xValue = source.getManaCostsToPay().getX();
            for (UUID uuid : watcher.damagedPlayers) {
                for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), uuid, game)) {
                    if (permanent.getControllerId().equals(uuid) && permanent.getManaCost().convertedManaCost() == xValue) {
                        permanent.destroy(source.getId(), game, false);
                    }
                }
            }
        }
        return true;
    }
}

class SteelHellkiteWatcher extends WatcherImpl<SteelHellkiteWatcher> {

    public List<UUID> damagedPlayers = new ArrayList<UUID>();

    public SteelHellkiteWatcher() {
        super("SteelHellkiteWatcher", WatcherScope.CARD);
    }

    public SteelHellkiteWatcher(final SteelHellkiteWatcher watcher) {
        super(watcher);
        for (UUID playerId: watcher.damagedPlayers) {
            damagedPlayers.add(playerId);
        }
    }

    @Override
    public SteelHellkiteWatcher copy() {
        return new SteelHellkiteWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            UUID sourceId = damageEvent.getSourceId();
            Permanent permanent = game.getPermanent(sourceId);
            if (sourceId != null && permanent != null && permanent.getName().equals("Steel Hellkite")) {
                if (!damagedPlayers.contains(event.getTargetId())) {
                    damagedPlayers.add(event.getTargetId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedPlayers.clear();
    }

}
