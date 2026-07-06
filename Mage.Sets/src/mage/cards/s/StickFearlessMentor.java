package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class StickFearlessMentor extends CardImpl {

    public StickFearlessMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever a source you control deals damage to you, exile the top card of your library. You may play it until the end of your next turn. This ability triggers only once each turn.
        this.addAbility(new StickFearlessMentorTriggeredAbility().setTriggersLimitEachTurn(1));
    }

    private StickFearlessMentor(final StickFearlessMentor card) {
        super(card);
    }

    @Override
    public StickFearlessMentor copy() {
        return new StickFearlessMentor(this);
    }
}

class StickFearlessMentorTriggeredAbility extends TriggeredAbilityImpl {

    StickFearlessMentorTriggeredAbility() {
        super(
            Zone.BATTLEFIELD,
            new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
                .withTextOptions("it", true)
        );
        setTriggerPhrase("Whenever a source you control deals damage to you, ");
    }

    private StickFearlessMentorTriggeredAbility(final StickFearlessMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StickFearlessMentorTriggeredAbility copy() {
        return new StickFearlessMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getTargetId()) && isControlledBy(game.getControllerId(event.getSourceId()))) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }
}
