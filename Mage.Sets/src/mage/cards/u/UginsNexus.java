package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SkipExtraTurnsAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class UginsNexus extends CardImpl {

    public UginsNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // If a player would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SkipExtraTurnsAbility());
        
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

class UginsNexusExileEffect extends ReplacementEffectImpl {

    UginsNexusExileEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would be put into a graveyard from the battlefield, instead exile it and take an extra turn after this one";
    }

    private UginsNexusExileEffect(final UginsNexusExileEffect effect) {
        super(effect);
    }

    @Override
    public UginsNexusExileEffect copy() {
        return new UginsNexusExileEffect(this);
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
