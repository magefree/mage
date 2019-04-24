
package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class UrzasMiter extends CardImpl {

    public UrzasMiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        Watcher watcher = new UrzasMiterWatcher();        
        // Whenever an artifact you control is put into a graveyard from the battlefield, if it wasn't sacrificed, you may pay {3}. If you do, draw a card.
        Effect effect = new UrzasMiterDoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(3));
        effect.setText("you may pay {3}. If you do, draw a card.");
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
