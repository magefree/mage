package mage.cards.z;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZenithFestival extends CardImpl {

    public ZenithFestival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Exile the top X cards of your library. You may play them until the end of your next turn.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(
                GetXValue.instance, false, Duration.UntilEndOfYourNextTurn
        ));

        // Harmonize {X}{R}{R}
        this.addAbility(new HarmonizeAbility(this, "{X}{R}{R}"));
    }

    private ZenithFestival(final ZenithFestival card) {
        super(card);
    }

    @Override
    public ZenithFestival copy() {
        return new ZenithFestival(this);
    }
}
