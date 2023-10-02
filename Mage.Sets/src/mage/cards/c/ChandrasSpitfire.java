

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ChandrasSpitfire extends CardImpl {

    public ChandrasSpitfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ChandrasSpitfireAbility());
    }

    private ChandrasSpitfire(final ChandrasSpitfire card) {
        super(card);
    }

    @Override
    public ChandrasSpitfire copy() {
        return new ChandrasSpitfire(this);
    }

}

class ChandrasSpitfireAbility extends TriggeredAbilityImpl {

    public ChandrasSpitfireAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(3, 0, Duration.EndOfTurn), false);
    }

    private ChandrasSpitfireAbility(final ChandrasSpitfireAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        return !damageEvent.isCombatDamage() && game.getOpponents(controllerId).contains(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent is dealt noncombat damage, {this} gets +3/+0 until end of turn.";
    }

    @Override
    public ChandrasSpitfireAbility copy() {
        return new ChandrasSpitfireAbility(this);
    }

}
