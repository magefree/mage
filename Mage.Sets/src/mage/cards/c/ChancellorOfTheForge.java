package mage.cards.c;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.PhyrexianGoblinHasteToken;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class ChancellorOfTheForge extends CardImpl {

    private static String abilityText = "at the beginning of the first upkeep, create a 1/1 red Goblin creature token with haste";

    public ChancellorOfTheForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You may reveal this card from your opening hand. If you do, at the beginning of the first upkeep, create a 1/1 red Goblin creature token with haste.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheForgeDelayedTriggeredAbility(), abilityText));

        // When Chancellor of the Forge enters the battlefield, create X 1/1 red Goblin creature tokens with haste, where X is the number of creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGoblinHasteToken(), CreaturesYouControlCount.instance), false)
                .addHint(CreaturesYouControlHint.instance));
    }

    private ChancellorOfTheForge(final ChancellorOfTheForge card) {
        super(card);
    }

    @Override
    public ChancellorOfTheForge copy() {
        return new ChancellorOfTheForge(this);
    }
}

class ChancellorOfTheForgeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheForgeDelayedTriggeredAbility() {
        super(new CreateTokenEffect(new PhyrexianGoblinHasteToken()));
    }

    ChancellorOfTheForgeDelayedTriggeredAbility(ChancellorOfTheForgeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
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
