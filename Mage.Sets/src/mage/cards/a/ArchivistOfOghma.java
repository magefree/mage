package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchivistOfOghma extends CardImpl {

    public ArchivistOfOghma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever an opponent searches their library, you gain 1 life and draw a card.
        this.addAbility(new ArchivistOfOghmaTriggeredAbility());
    }

    private ArchivistOfOghma(final ArchivistOfOghma card) {
        super(card);
    }

    @Override
    public ArchivistOfOghma copy() {
        return new ArchivistOfOghma(this);
    }
}

class ArchivistOfOghmaTriggeredAbility extends TriggeredAbilityImpl {

    public ArchivistOfOghmaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1), false);
        this.addEffect(new DrawCardSourceControllerEffect(1));
    }

    public ArchivistOfOghmaTriggeredAbility(final ArchivistOfOghmaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchivistOfOghmaTriggeredAbility copy() {
        return new ArchivistOfOghmaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SEARCHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(event.getPlayerId())
                && game.getOpponents(getControllerId()).contains(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent searches their library, you gain 1 life and draw a card.";
    }
}