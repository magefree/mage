
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class OverflowingInsight extends CardImpl {

    public OverflowingInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        // Target player draws seven cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private OverflowingInsight(final OverflowingInsight card) {
        super(card);
    }

    @Override
    public OverflowingInsight copy() {
        return new OverflowingInsight(this);
    }
}
