package mage.cards.h;

import mage.Mana;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class HighTide extends CardImpl {

    public HighTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Until end of turn, whenever a player taps an Island for mana, that player adds {U}.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new HighTideTriggeredAbility()));
    }

    private HighTide(final HighTide card) {
        super(card);
    }

    @Override
    public HighTide copy() {
        return new HighTide(this);
    }
}

class HighTideTriggeredAbility extends DelayedTriggeredManaAbility {

    HighTideTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.U), "their"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    private HighTideTriggeredAbility(HighTideTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        if (permanent == null || !permanent.hasSubtype(SubType.ISLAND, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
        return true;
    }

    @Override
    public HighTideTriggeredAbility copy() {
        return new HighTideTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player taps an Island for mana, that player adds {U}";
    }
}
