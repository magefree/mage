
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author North
 */
public final class RapaciousOne extends CardImpl {

    public RapaciousOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new RapaciousOneTriggeredAbility());
    }

    private RapaciousOne(final RapaciousOne card) {
        super(card);
    }

    @Override
    public RapaciousOne copy() {
        return new RapaciousOne(this);
    }
}

class RapaciousOneTriggeredAbility extends TriggeredAbilityImpl {

    public RapaciousOneTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private RapaciousOneTriggeredAbility(final RapaciousOneTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RapaciousOneTriggeredAbility copy() {
        return new RapaciousOneTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.sourceId) && ((DamagedPlayerEvent) event).isCombatDamage()) {
            this.getEffects().clear();
            this.addEffect(new CreateTokenEffect(new EldraziSpawnToken(), event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, create that many 0/1 colorless Eldrazi Spawn creature tokens. They have \"Sacrifice this creature: Add {C}.\"";
    }
}
