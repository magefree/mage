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
package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public class UrzasMiter extends CardImpl {

    public UrzasMiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        Watcher watcher = new UrzasMiterWatcher();        
        // Whenever an artifact you control is put into a graveyard from the battlefield, if it wasn't sacrificed, you may pay {3}. If you do, draw a card.
        Effect effect = new UrzasMiterDoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1));
        effect.setText("you may pay {1}. If you do, you gain 1 life.");
        Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                effect, new FilterControlledArtifactPermanent(),
                "Whenever an artifact you control is put into a graveyard from the battlefield, ", true);
        this.addAbility(ability, watcher);
    }

    public UrzasMiter(final UrzasMiter card) {
        super(card);
    }

    @Override
    public UrzasMiter copy() {
        return new UrzasMiter(this);
    }
}

class UrzasMiterDoIfCostPaid extends DoIfCostPaid {
    
    
    public UrzasMiterDoIfCostPaid(Effect effect, Cost cost){
        super(effect, cost);
    }
  
    @Override
    public boolean apply(Game game, Ability source) {
        UrzasMiterWatcher watcher = (UrzasMiterWatcher) game.getState().getWatchers().get(UrzasMiterWatcher.class.getSimpleName());
        if(!watcher.cards.contains(source.getFirstTarget()))
            return super.apply(game, source);
        
        return false;
    }  
        
}

class UrzasMiterWatcher extends Watcher {

    List<UUID> cards;

    public UrzasMiterWatcher() {
        super(UrzasMiterWatcher.class.getSimpleName(), WatcherScope.PLAYER);
        this.cards = new ArrayList<>();
    }

    public UrzasMiterWatcher(final UrzasMiterWatcher watcher) {
        super(watcher);
        this.cards = new ArrayList<>();
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT) {            
            cards.add(event.getTargetId());
        }
    }

    @Override
    public UrzasMiterWatcher copy() {
        return new UrzasMiterWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
