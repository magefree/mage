
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class TerritorialDispute extends CardImpl {

    public TerritorialDispute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}{R}");

        // At the beginning of your upkeep, sacrifice Territorial Dispute unless you sacrifice a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))),
                TargetController.YOU, 
                false));
        
        // Players can't play lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TerritorialDisputeEffect()));
    }

    private TerritorialDispute(final TerritorialDispute card) {
        super(card);
    }

    @Override
    public TerritorialDispute copy() {
        return new TerritorialDispute(this);
    }
}

class TerritorialDisputeEffect extends ContinuousRuleModifyingEffectImpl {

    public TerritorialDisputeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "Players can't play lands";
    }
    
    private TerritorialDisputeEffect(final TerritorialDisputeEffect effect) {
        super(effect);
    }

    @Override
    public TerritorialDisputeEffect copy() {
        return new TerritorialDisputeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
    
}