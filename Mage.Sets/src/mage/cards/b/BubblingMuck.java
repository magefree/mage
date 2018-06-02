
package mage.cards.b;

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
 * @author spjspj
 */
public final class BubblingMuck extends CardImpl {

    public BubblingMuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Until end of turn, whenever a player taps a Swamp for mana, that player adds {B}.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BubblingMuckTriggeredAbility()));
    }

    public BubblingMuck(final BubblingMuck card) {
        super(card);
    }

    @Override
    public BubblingMuck copy() {
        return new BubblingMuck(this);
    }
}

class BubblingMuckTriggeredAbility extends DelayedTriggeredManaAbility {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp");

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    public BubblingMuckTriggeredAbility() {
        super(new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.B), "their"), Duration.EndOfTurn, false);
        this.usesStack = false;
    }

    public BubblingMuckTriggeredAbility(BubblingMuckTriggeredAbility ability) {
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
    public BubblingMuckTriggeredAbility copy() {
        return new BubblingMuckTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player taps a Swamp for mana, that player adds {B}";
    }
}
