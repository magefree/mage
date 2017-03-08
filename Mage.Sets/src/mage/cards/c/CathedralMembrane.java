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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PhyrexianManaSpellCostReductionAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class CathedralMembrane extends CardImpl {

    public CathedralMembrane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W/P}");
        this.subtype.add("Wall");

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {W/P} can be paid with either {W} or 2 life.
        this.addAbility(new PhyrexianManaSpellCostReductionAbility(this.getManaCost()));

        this.addAbility(DefenderAbility.getInstance());

        // When Cathedral Membrane dies during combat, it deals 6 damage to each creature it blocked this combat.
        this.addAbility(new CathedralMembraneAbility(), new CathedralMembraneWatcher());

    }

    public CathedralMembrane(final CathedralMembrane card) {
        super(card);
    }

    @Override
    public CathedralMembrane copy() {
        return new CathedralMembrane(this);
    }
}

class CathedralMembraneAbility extends ZoneChangeTriggeredAbility {

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

class CathedralMembraneEffect extends OneShotEffect {

    public CathedralMembraneEffect() {
        super(Outcome.Damage);
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
        CathedralMembraneWatcher watcher = (CathedralMembraneWatcher) game.getState().getWatchers().get("CathedralMembraneWatcher", source.getSourceId());
        if (watcher != null) {
            for (UUID uuid : watcher.blockedCreatures) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null) {
                    permanent.damage(6, source.getSourceId(), game, false, true);
                }
            }
        }
        return true;
    }
}

class CathedralMembraneWatcher extends Watcher {

    public List<UUID> blockedCreatures = new ArrayList<>();

    public CathedralMembraneWatcher() {
        super("CathedralMembraneWatcher", WatcherScope.CARD);
    }

    public CathedralMembraneWatcher(final CathedralMembraneWatcher watcher) {
        super(watcher);
        this.blockedCreatures.addAll(watcher.blockedCreatures);
    }

    @Override
    public CathedralMembraneWatcher copy() {
        return new CathedralMembraneWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED && event.getSourceId().equals(sourceId)) {
            if (!blockedCreatures.contains(event.getTargetId())) {
                blockedCreatures.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        blockedCreatures.clear();
    }

}
