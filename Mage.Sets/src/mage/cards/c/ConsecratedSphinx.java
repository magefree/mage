
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author Loki
 */
public final class ConsecratedSphinx extends CardImpl {

    public ConsecratedSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent draws a card, you may draw two cards.
        this.addAbility(new ConsecratedSphinxTriggeredAbility());
    }

    private ConsecratedSphinx(final ConsecratedSphinx card) {
        super(card);
    }

    @Override
    public ConsecratedSphinx copy() {
        return new ConsecratedSphinx(this);
    }

}

class ConsecratedSphinxTriggeredAbility extends TriggeredAbilityImpl {

    ConsecratedSphinxTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), true);
    }

    private ConsecratedSphinxTriggeredAbility(final ConsecratedSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ConsecratedSphinxTriggeredAbility copy() {
        return new ConsecratedSphinxTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getPlayerId())
                && game.getPermanent(sourceId) != null; // the Sphinx must be on the battlefield
    }

    @Override
    public String getRule() {
        return "Whenever an opponent draws a card, you may draw two cards.";
    }
}
