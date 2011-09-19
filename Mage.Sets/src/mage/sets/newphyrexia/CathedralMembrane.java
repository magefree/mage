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
package mage.sets.newphyrexia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TurnPhase;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward
 */
public class CathedralMembrane extends CardImpl<CathedralMembrane> {

    public CathedralMembrane(UUID ownerId) {
        super(ownerId, 5, "Cathedral Membrane", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{WP}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Wall");

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // <i>({WP} can be paid with either {W} or 2 life.)</i>
        this.addAbility(DefenderAbility.getInstance());
        
        // When Cathedral Membrane dies during combat, it deals 6 damage to each creature it blocked this combat.
        this.addWatcher(new CathedralMembraneWatcher());
        this.addAbility(new CathedralMembraneAbility());
        
    }

    public CathedralMembrane(final CathedralMembrane card) {
        super(card);
    }

    @Override
    public CathedralMembrane copy() {
        return new CathedralMembrane(this);
    }
}

class CathedralMembraneAbility extends ZoneChangeTriggeredAbility<CathedralMembraneAbility> {

	public CathedralMembraneAbility() {
		super(Zone.BATTLEFIELD, Zone.GRAVEYARD, new CathedralMembraneEffect(), "When {this} dies during combat, ", false);
	}

	public CathedralMembraneAbility(CathedralMembraneAbility ability) {
		super(ability);
	}

	@Override
	public CathedralMembraneAbility copy() {
		return new CathedralMembraneAbility(this);
	}

    @Override
	public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            if (game.getPhase().getType() == TurnPhase.COMBAT) {
                return true;            
            }
        }
        return false;
    }

}

class CathedralMembraneEffect extends OneShotEffect<CathedralMembraneEffect> {

	public CathedralMembraneEffect() {
		super(Constants.Outcome.Damage);
		staticText = "it deals 6 damage to each creature it blocked this combat";
	}

	public CathedralMembraneEffect(final CathedralMembraneEffect effect) {
		super(effect);
	}

	@Override
	public CathedralMembraneEffect copy() {
		return new CathedralMembraneEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		CathedralMembraneWatcher watcher = (CathedralMembraneWatcher) game.getState().getWatchers().get(source.getControllerId(), "CathedralMembraneWatcher");
		if (watcher != null && watcher.blockedCreatures.containsKey(source.getSourceId())) {
			Set<UUID> creatures = watcher.blockedCreatures.get(source.getSourceId());
			for (UUID uuid : creatures) {
				Permanent permanent = game.getPermanent(uuid);
                if (permanent != null) {
                    permanent.damage(6, source.getSourceId(), game, true, false);
                }
			}
		}
		return true;
	}
}

class CathedralMembraneWatcher extends WatcherImpl<CathedralMembraneWatcher> {

	public Map<UUID, Set<UUID>> blockedCreatures = new HashMap<UUID, Set<UUID>>();

	public CathedralMembraneWatcher() {
		super("CathedralMembraneWatcher");
	}

	public CathedralMembraneWatcher(final CathedralMembraneWatcher watcher) {
		super(watcher);
	}

	@Override
	public CathedralMembraneWatcher copy() {
		return new CathedralMembraneWatcher(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED && event.getSourceId().equals(sourceId)) {
            Set<UUID> creatures = blockedCreatures.get(sourceId);
            if (creatures != null) {
                creatures.add(event.getTargetId());
            } else {
                creatures = new HashSet<UUID>();
                creatures.add(event.getTargetId());
                blockedCreatures.put(sourceId, creatures);
            }
        }
	}

	@Override
	public void reset() {
		super.reset();
		blockedCreatures.clear();
	}

}
