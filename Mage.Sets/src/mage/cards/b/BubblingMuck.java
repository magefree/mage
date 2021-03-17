package mage.cards.b;

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
 * @author spjspj
 */
public final class BubblingMuck extends CardImpl {

    public BubblingMuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Until end of turn, whenever a player taps a Swamp for mana, that player adds {B}.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BubblingMuckTriggeredAbility()));
    }

    private BubblingMuck(final BubblingMuck card) {
        super(card);
    }

    @Override
    public BubblingMuck copy() {
        return new BubblingMuck(this);
    }
}

class BubblingMuckTriggeredAbility extends DelayedTriggeredManaAbility {

    BubblingMuckTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.B), "their"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    private BubblingMuckTriggeredAbility(BubblingMuckTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = ((TappedForManaEvent) event).getPermanent();
        if (land == null || !land.hasSubtype(SubType.SWAMP, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(land.getControllerId()));
        return true;
    }

    @Override
    public BubblingMuckTriggeredAbility copy() {
        return new BubblingMuckTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player taps a Swamp for mana, that player adds {B}";
    }
}
