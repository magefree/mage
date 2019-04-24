
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
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
 * @author Quercitron
 */
public final class GravebaneZombie extends CardImpl {

    public GravebaneZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // If Gravebane Zombie would die, put Gravebane Zombie on top of its owner's library instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GravebaneZombieEffect()));
    }

    public GravebaneZombie(final GravebaneZombie card) {
        super(card);
    }

    @Override
    public GravebaneZombie copy() {
        return new GravebaneZombie(this);
    }
}

class GravebaneZombieEffect extends ReplacementEffectImpl {

    GravebaneZombieEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If {this} would die, put Gravebane Zombie on top of its owner's library instead";
    }

    GravebaneZombieEffect(final GravebaneZombieEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null) {
            if (permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true)) {
                game.informPlayers(permanent.getName() + " was put on the top of its owner's library");
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) && ((ZoneChangeEvent) event).isDiesEvent();
    }

    @Override
    public GravebaneZombieEffect copy() {
        return new GravebaneZombieEffect(this);
    }
}