
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ChargingTuskodon extends CardImpl {

    public ChargingTuskodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // If Charging Tuskodon would deal combat damage to a player, it deals double that damage to that player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChargingTuskodonEffect()));
    }

    private ChargingTuskodon(final ChargingTuskodon card) {
        super(card);
    }

    @Override
    public ChargingTuskodon copy() {
        return new ChargingTuskodon(this);
    }
}

class ChargingTuskodonEffect extends ReplacementEffectImpl {

    public ChargingTuskodonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If {this} would deal combat damage to a player, it deals double that damage to that player instead";
    }

    private ChargingTuskodonEffect(final ChargingTuskodonEffect effect) {
        super(effect);
    }

    @Override
    public ChargingTuskodonEffect copy() {
        return new ChargingTuskodonEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId()) && ((DamageEvent) event).isCombatDamage();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}
