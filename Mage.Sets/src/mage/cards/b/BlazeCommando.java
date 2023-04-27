

package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SoldierTokenWithHaste;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */


public final class BlazeCommando extends CardImpl {

    public BlazeCommando (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{W}");
        this.subtype.add(SubType.MINOTAUR, SubType.SOLDIER);


        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever an instant or sorcery spell you control deals damage, create two 1/1 red and white Soldier creature tokens with haste.
        this.addAbility(new BlazeCommandoTriggeredAbility());

    }

    public BlazeCommando (final BlazeCommando card) {
        super(card);
    }

    @Override
    public BlazeCommando copy() {
        return new BlazeCommando(this);
    }

}

class BlazeCommandoTriggeredAbility extends TriggeredAbilityImpl {

    private final List<UUID> handledStackObjects = new ArrayList<>();

    public BlazeCommandoTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierTokenWithHaste(), 2), false);
        setTriggerPhrase("Whenever an instant or sorcery spell you control deals damage, ");
    }

    public BlazeCommandoTriggeredAbility(final BlazeCommandoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlazeCommandoTriggeredAbility copy() {
        return new BlazeCommandoTriggeredAbility(this);
    }

    @Override
    public void reset(Game game) {
        /**
         * Blaze Commando's ability triggers each time an instant or sorcery spell you control
         * deals damage (or, put another way, the number of times the word "deals" appears in
         * its instructions), no matter how much damage is dealt or how many players or permanents
         * are dealt damage. For example, if you cast Punish the Enemy and it "deals 3 damage to
         * target player and 3 damage to target creature," Blaze Commando's ability will trigger
         * once and you'll get two Soldier tokens.
         */
        handledStackObjects.clear();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject damageSource = game.getObject(event.getSourceId());
            if (damageSource != null) {
                if (damageSource.isInstantOrSorcery(game)) {
                    if (!handledStackObjects.contains(damageSource.getId())) {
                        handledStackObjects.add(damageSource.getId());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
