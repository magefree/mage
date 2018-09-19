package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class ChanceForGlory extends CardImpl {

    public ChanceForGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");

        // Creatures you control gain indestructible. Take an extra turn after this one. At the beginning of that turn's end step, you lose the game.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfGame
        ).setText("Creatures you control gain indestructible."));
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect(true));
    }

    public ChanceForGlory(final ChanceForGlory card) {
        super(card);
    }

    @Override
    public ChanceForGlory copy() {
        return new ChanceForGlory(this);
    }
}
