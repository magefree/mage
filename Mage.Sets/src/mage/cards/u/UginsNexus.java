
package mage.cards.u;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class UginsNexus extends CardImpl {

    public UginsNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        addSuperType(SuperType.LEGENDARY);

        // If a player would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UginsNexusSkipExtraTurnsEffect()));
        
        // If Ugin's Nexus would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UginsNexusExileEffect()));
    }

    private UginsNexus(final UginsNexus card) {
        super(card);
    }

    @Override
    public UginsNexus copy() {
        return new UginsNexus(this);
    }
}

class UginsNexusSkipExtraTurnsEffect extends ReplacementEffectImpl {

    public UginsNexusSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If a player would begin an extra turn, that player skips that turn instead";
    }

    public UginsNexusSkipExtraTurnsEffect(final UginsNexusSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public UginsNexusSkipExtraTurnsEffect copy() {
        return new UginsNexusSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXTRA_TURN;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}

class UginsNexusExileEffect extends ReplacementEffectImpl {

    public UginsNexusExileEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one";
    }

    public UginsNexusExileEffect(final UginsNexusExileEffect effect) {
        super(effect);
    }

    @Override
    public UginsNexusExileEffect copy() {
        return new UginsNexusExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        if (permanent != null) {
            permanent.moveToExile(null, "", source, game);
            new AddExtraTurnControllerEffect().apply(game, source);
            return true;
        }
        return false;
    }
    
    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getToZone() == Zone.GRAVEYARD && zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }

}
