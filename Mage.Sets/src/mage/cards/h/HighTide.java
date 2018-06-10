
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.mana.DelayedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class HighTide extends CardImpl {

    public HighTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Until end of turn, whenever a player taps an Island for mana, that player adds {U}.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new HighTideTriggeredAbility()));

    }

    public HighTide(final HighTide card) {
        super(card);
    }

    @Override
    public HighTide copy() {
        return new HighTide(this);
    }
}

class HighTideTriggeredAbility extends DelayedTriggeredManaAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Island");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public HighTideTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.U), "their"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    public HighTideTriggeredAbility(HighTideTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        if (land != null && filter.match(land, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(land.getControllerId()));
            }
            return true;
        }
        return false;
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
