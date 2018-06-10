

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public final class SamuraiOfThePaleCurtain extends CardImpl {

    public SamuraiOfThePaleCurtain (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));
        // If a permanent would be put into a graveyard, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SamuraiOfThePaleCurtainEffect()));

    }

    public SamuraiOfThePaleCurtain (final SamuraiOfThePaleCurtain card) {
        super(card);
    }

    @Override
    public SamuraiOfThePaleCurtain copy() {
        return new SamuraiOfThePaleCurtain(this);
    }

}


class SamuraiOfThePaleCurtainEffect extends ReplacementEffectImpl {

    public SamuraiOfThePaleCurtainEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a permanent would be put into a graveyard, exile it instead";
    }

    public SamuraiOfThePaleCurtainEffect(final SamuraiOfThePaleCurtainEffect effect) {
        super(effect);
    }

    @Override
    public SamuraiOfThePaleCurtainEffect copy() {
        return new SamuraiOfThePaleCurtainEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        if (permanent != null) {
            return permanent.moveToExile(null, "", source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
        return zEvent.getToZone() == Zone.GRAVEYARD;
    }

}
