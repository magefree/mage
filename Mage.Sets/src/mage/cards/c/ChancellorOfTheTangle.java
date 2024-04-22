
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheTangle extends CardImpl {

    private static String abilityText = "at the beginning of your first main phase, add {G}";

    public ChancellorOfTheTangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // You may reveal this card from your opening hand. If you do, at the beginning of your first main phase, add {G}.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheTangleDelayedTriggeredAbility(), abilityText));

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(ReachAbility.getInstance());
    }

    private ChancellorOfTheTangle(final ChancellorOfTheTangle card) {
        super(card);
    }

    @Override
    public ChancellorOfTheTangle copy() {
        return new ChancellorOfTheTangle(this);
    }
}

class ChancellorOfTheTangleDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ChancellorOfTheTangleDelayedTriggeredAbility () {
        super(new BasicManaEffect(Mana.GreenMana(1)));
    }

    private ChancellorOfTheTangleDelayedTriggeredAbility(final ChancellorOfTheTangleDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(controllerId);
    }
    @Override
    public ChancellorOfTheTangleDelayedTriggeredAbility copy() {
        return new ChancellorOfTheTangleDelayedTriggeredAbility(this);
    }
}
