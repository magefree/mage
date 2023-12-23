
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class SulfuricVortex extends CardImpl {

    public SulfuricVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}{R}");


        // At the beginning of each player's upkeep, Sulfuric Vortex deals 2 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"), TargetController.ANY, false, true));        
        
        // If a player would gain life, that player gains no life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SulfuricVortexReplacementEffect()));
        
    }

    private SulfuricVortex(final SulfuricVortex card) {
        super(card);
    }

    @Override
    public SulfuricVortex copy() {
        return new SulfuricVortex(this);
    }
}

class SulfuricVortexReplacementEffect extends ReplacementEffectImpl {

    public SulfuricVortexReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a player would gain life, that player gains no life instead";
    }

    private SulfuricVortexReplacementEffect(final SulfuricVortexReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SulfuricVortexReplacementEffect copy() {
        return new SulfuricVortexReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }    
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

}
