
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class AggressiveMining extends CardImpl {

    public AggressiveMining(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // You can't play lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AggressiveMiningEffect()));
        
        // Sacrifice a land: Draw two cards.  Activate this ability only once each turn.
        Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")));
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), cost));
    }

    private AggressiveMining(final AggressiveMining card) {
        super(card);
    }

    @Override
    public AggressiveMining copy() {
        return new AggressiveMining(this);
    }
}

class AggressiveMiningEffect extends ContinuousRuleModifyingEffectImpl {

    public AggressiveMiningEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "You can't play lands";
    }
    
    public AggressiveMiningEffect(final AggressiveMiningEffect effect) {
        super(effect);
    }

    @Override
    public AggressiveMiningEffect copy() {
        return new AggressiveMiningEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
    
}
