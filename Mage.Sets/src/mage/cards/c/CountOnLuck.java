package mage.cards.c;

import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CountOnLuck extends CardImpl {

    public CountOnLuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}{R}");

        // At the beginning of your upkeep, exile the top card of your library. You may play that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)));
    }

    private CountOnLuck(final CountOnLuck card) {
        super(card);
    }

    @Override
    public CountOnLuck copy() {
        return new CountOnLuck(this);
    }
}
