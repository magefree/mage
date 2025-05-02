package mage.cards.f;

import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
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
import mage.game.permanent.Permanent;
import mage.game.permanent.token.HumanKnightToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ForthEorlingas extends CardImpl {

    public ForthEorlingas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Create X 2/2 red Human Knight creature tokens with trample and haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new HumanKnightToken(), GetXValue.instance, false, false
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
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (!event.isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((DamagedBatchForPlayersEvent) event, game).isEmpty();
    }

    @Override
    public ForthEorlingasTriggeredAbility copy() {
        return new ForthEorlingasTriggeredAbility(this);
    }
}
