
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class WallOfHope extends CardImpl {

    public WallOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Wall of Hope is dealt damage, you gain that much life.
        this.addAbility(new WallOfHopeTriggeredAbility());

    }

    private WallOfHope(final WallOfHope card) {
        super(card);
    }

    @Override
    public WallOfHope copy() {
        return new WallOfHope(this);
    }
}

class WallOfHopeTriggeredAbility extends TriggeredAbilityImpl {

    public WallOfHopeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WallOfHopeGainLifeEffect());
        setTriggerPhrase("Whenever {this} is dealt damage, ");
    }

    public WallOfHopeTriggeredAbility(final WallOfHopeTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public WallOfHopeTriggeredAbility copy() {
        return new WallOfHopeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.sourceId)) {
            return false;
        }
        this.getEffects().setValue("damageAmount", event.getAmount());
        return true;
    }
}

class WallOfHopeGainLifeEffect extends OneShotEffect {

    public WallOfHopeGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life";
    }

    public WallOfHopeGainLifeEffect(final WallOfHopeGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public WallOfHopeGainLifeEffect copy() {
        return new WallOfHopeGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game, source);
        }
        return true;
    }

}
