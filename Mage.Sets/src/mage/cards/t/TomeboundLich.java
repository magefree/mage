package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomeboundLich extends CardImpl {

    public TomeboundLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Tomebound Lich enters the battlefield or deals combat damage to a player, draw a card, then discard a card.
        this.addAbility(new TomeboundLichTriggeredAbility());
    }

    private TomeboundLich(final TomeboundLich card) {
        super(card);
    }

    @Override
    public TomeboundLich copy() {
        return new TomeboundLich(this);
    }
}

class TomeboundLichTriggeredAbility extends TriggeredAbilityImpl {

    TomeboundLichTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1), false);
    }

    private TomeboundLichTriggeredAbility(final TomeboundLichTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TomeboundLichTriggeredAbility copy() {
        return new TomeboundLichTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && event.getSourceId().equals(this.getSourceId())
                && ((DamagedPlayerEvent) event).isCombatDamage()) {
            return true;
        }
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} enters the battlefield or deals combat damage " +
                "to a player, draw a card, then discard a card.";
    }

}
