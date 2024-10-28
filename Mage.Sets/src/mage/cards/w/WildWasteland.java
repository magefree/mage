package mage.cards.w;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class WildWasteland extends CardImpl {

    public WildWasteland(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");


        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // At the beginning of your upkeep, exile the top two cards of your library. You may play those cards this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, Duration.EndOfTurn), false));
    }

    private WildWasteland(final WildWasteland card) {
        super(card);
    }

    @Override
    public WildWasteland copy() {
        return new WildWasteland(this);
    }
}
