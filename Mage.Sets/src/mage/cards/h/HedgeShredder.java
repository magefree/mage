package mage.cards.h;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTargets;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class HedgeShredder extends CardImpl {

    public HedgeShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Hedge Shredder attacks, you may mill two cards.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(2), true));

        // Whenever one or more land cards are put into your graveyard from your library, put them onto the battlefield tapped.
        this.addAbility(new HedgeShredderTriggeredAbility());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private HedgeShredder(final HedgeShredder card) {
        super(card);
    }

    @Override
    public HedgeShredder copy() {
        return new HedgeShredder(this);
    }
}

class HedgeShredderTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    HedgeShredderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(true));
    }

    private HedgeShredderTriggeredAbility(final HedgeShredderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HedgeShredderTriggeredAbility copy() {
        return new HedgeShredderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getFromZone() != Zone.LIBRARY || event.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        return card != null && card.isLand(game) && card.isOwnedBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<Card> set = getFilteredEvents((ZoneChangeBatchEvent) event, game)
                .stream()
                .map(ZoneChangeEvent::getTargetId)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (set.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(set, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard " +
                "from your library, put them onto the battlefield tapped.";
    }
}
