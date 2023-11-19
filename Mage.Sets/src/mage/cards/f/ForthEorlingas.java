package mage.cards.f;

import java.util.UUID;

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
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanKnightToken;

/**
 *
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

class ForthEorlingasTriggeredAbility extends DelayedTriggeredAbility {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedBatchForPlayersEvent dEvent = (DamagedBatchForPlayersEvent) event;
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            if (!damagedEvent.isCombatDamage()) {
                continue;
            }
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent != null && permanent.isControlledBy(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ForthEorlingasTriggeredAbility copy() {
        return new ForthEorlingasTriggeredAbility(this);
    }
}
