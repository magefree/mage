
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class TimeVault extends CardImpl {

    public TimeVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Time Vault enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // Time Vault doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        
        // If you would begin your turn while Time Vault is tapped, you may skip that turn instead. If you do, untap Time Vault.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TimeVaultReplacementEffect()));
        
        // {tap}: Take an extra turn after this one.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddExtraTurnControllerEffect(), new TapSourceCost()));

    }

    private TimeVault(final TimeVault card) {
        super(card);
    }

    @Override
    public TimeVault copy() {
        return new TimeVault(this);
    }
}

class TimeVaultReplacementEffect extends ReplacementEffectImpl {
    
    TimeVaultReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Untap);
        staticText = "If you would begin your turn while {this} is tapped, you may skip that turn instead. If you do, untap Time Vault.";        
    }
    
    private TimeVaultReplacementEffect(final TimeVaultReplacementEffect effect) {
        super(effect);
    }
    
    @Override
    public TimeVaultReplacementEffect copy() {
        return new TimeVaultReplacementEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_TURN;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.isTapped()) {
                return true;
            }
        }
        return false;
    }
       
    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            if (player.chooseUse(Outcome.Untap, "Skip your turn to untap " + permanent.getName() + '?', source, game)) {
                permanent.untap(game);
                game.informPlayers(player.getLogName() + " skips their turn to untap " + permanent.getLogName());
                return true;
            }
        }
        return false;
    }
}
