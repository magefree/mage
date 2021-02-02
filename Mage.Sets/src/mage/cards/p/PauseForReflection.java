package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class PauseForReflection extends CardImpl {

    public PauseForReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Precent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
    }

    private PauseForReflection(final PauseForReflection card) {
        super(card);
    }

    @Override
    public PauseForReflection copy() {
        return new PauseForReflection(this);
    }
}
