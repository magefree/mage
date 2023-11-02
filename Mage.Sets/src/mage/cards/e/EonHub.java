package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author nick.myers
 */
public final class EonHub extends CardImpl {
 
    public EonHub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        
        // Players skip their upkeep steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUpkeepStepEffect()));
    }
    
    private EonHub(final EonHub card) {
        super(card);
    }
    
    @Override
    public EonHub copy() {
        return new EonHub(this);
    }
    
}

class SkipUpkeepStepEffect extends ContinuousRuleModifyingEffectImpl {
    
    public SkipUpkeepStepEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, false, false);
        staticText = "Players skip their upkeep steps";
    }
    
    private SkipUpkeepStepEffect(final SkipUpkeepStepEffect effect) {
        super(effect);
    }
    
    @Override
    public SkipUpkeepStepEffect copy() {
        return new SkipUpkeepStepEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return  controller != null && game.getState().getPlayersInRange(controller.getId(), game).contains(event.getPlayerId());
    }
}
