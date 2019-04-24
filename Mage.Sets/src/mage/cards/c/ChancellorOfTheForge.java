
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheForge extends CardImpl {

    private static String abilityText = "at the beginning of the first upkeep, create a 1/1 red Goblin creature token with haste";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ChancellorOfTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, create a 1/1 red Goblin creature token with haste.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheForgeDelayedTriggeredAbility(), abilityText));

        // When Chancellor of the Forge enters the battlefield, create X 1/1 red Goblin creature tokens with haste, where X is the number of creatures you control.
        DynamicValue value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GoblinToken(true), value), false));
    }

    public ChancellorOfTheForge(final ChancellorOfTheForge card) {
        super(card);
    }

    @Override
    public ChancellorOfTheForge copy() {
        return new ChancellorOfTheForge(this);
    }
}

class ChancellorOfTheForgeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheForgeDelayedTriggeredAbility() {
        super(new CreateTokenEffect(new GoblinToken(true)));
    }

    ChancellorOfTheForgeDelayedTriggeredAbility(ChancellorOfTheForgeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public ChancellorOfTheForgeDelayedTriggeredAbility copy() {
        return new ChancellorOfTheForgeDelayedTriggeredAbility(this);
    }
}
