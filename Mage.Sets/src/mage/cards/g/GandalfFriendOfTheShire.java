package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GandalfFriendOfTheShire extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery spells");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public GandalfFriendOfTheShire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may cast sorcery spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));

        // Whenever the Ring tempts you, if you chose a creature other than Gandalf, Friend of the Shire as your Ring-bearer, draw a card.
        this.addAbility(new GandalfFriendOfTheShireTriggeredAbility());
    }

    private GandalfFriendOfTheShire(final GandalfFriendOfTheShire card) {
        super(card);
    }

    @Override
    public GandalfFriendOfTheShire copy() {
        return new GandalfFriendOfTheShire(this);
    }
}

class GandalfFriendOfTheShireTriggeredAbility extends TriggeredAbilityImpl {

    GandalfFriendOfTheShireTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever the Ring tempts you, if you chose a creature other than {this} as your Ring-bearer, ");
    }

    private GandalfFriendOfTheShireTriggeredAbility(final GandalfFriendOfTheShireTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GandalfFriendOfTheShireTriggeredAbility copy() {
        return new GandalfFriendOfTheShireTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TEMPTED_BY_RING;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.isControlledBy(event.getPlayerId())
                && event.getTargetId() != null
                && !event.getTargetId().equals(this.getSourceId());
    }
}
