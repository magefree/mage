package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class Dreadhound extends CardImpl {

    public Dreadhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Dreadhound enters the battlefield, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // Whenever a creature dies or a creature card is put into a graveyard from a library, each opponent loses 1 life.
        this.addAbility(new DreadhoundTriggeredAbility());
    }

    private Dreadhound(final Dreadhound card) {
        super(card);
    }

    @Override
    public Dreadhound copy() {
        return new Dreadhound(this);
    }
}

class DreadhoundTriggeredAbility extends TriggeredAbilityImpl {

    public DreadhoundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(1));
    }

    private DreadhoundTriggeredAbility(final DreadhoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DreadhoundTriggeredAbility copy() {
        return new DreadhoundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                Permanent permanent = zEvent.getTarget();
                return permanent != null && permanent.isCreature(game);
            }
            if (zEvent.getFromZone() == Zone.LIBRARY) {
                Card card = game.getCard(zEvent.getTargetId());
                return card != null && card.isCreature(game);
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature dies or a creature card is put into a graveyard from a library, ";
    }
}
