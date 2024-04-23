package mage.cards.f;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.HumanKnightToken;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author Susucr
 */
public final class ForthEorlingas extends CardImpl {

    public ForthEorlingas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Create X 2/2 red Human Knight creature tokens with trample and haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new HumanKnightToken(), ManacostVariableValue.REGULAR, false, false
        ));

        // Whenever one or more creatures you control deal combat damage to one or more players this turn, you become the monarch.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ForthEorlingasTriggeredAbility()).concatBy("<br>")
        );
        this.getSpellAbility().addHint(MonarchHint.instance);
    }

    private ForthEorlingas(final ForthEorlingas card) {
        super(card);
    }

    @Override
    public ForthEorlingas copy() {
        return new ForthEorlingas(this);
    }
}

class ForthEorlingasTriggeredAbility extends DelayedTriggeredAbility implements BatchTriggeredAbility<DamagedPlayerEvent> {

    public ForthEorlingasTriggeredAbility() {
        super(new BecomesMonarchSourceEffect(), Duration.EndOfTurn, false);
        this.setTriggerPhrase("Whenever one or more creatures you control deal combat damage to one or more players this turn, ");
        this.addHint(MonarchHint.instance);
    }

    private ForthEorlingasTriggeredAbility(final ForthEorlingasTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public Stream<DamagedPlayerEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForPlayersEvent) event)
                .getEvents()
                .stream()
                .filter(DamagedPlayerEvent::isCombatDamage)
                .filter(e -> e.getAmount() > 0)
                .filter(e -> Optional
                        .of(e)
                        .map(DamagedPlayerEvent::getSourceId)
                        .map(game::getPermanentOrLKIBattlefield)
                        .filter(p -> p.isControlledBy(getControllerId()))
                        .isPresent()
                );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return filterBatchEvent(event, game).findAny().isPresent();
    }

    @Override
    public ForthEorlingasTriggeredAbility copy() {
        return new ForthEorlingasTriggeredAbility(this);
    }
}
