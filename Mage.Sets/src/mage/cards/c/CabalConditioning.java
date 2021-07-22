
package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author nigelzor
 */
public final class CabalConditioning extends CardImpl {

    public CabalConditioning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Any number of target players each discard a number of cards equal to the highest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(new HighestManaValueCount())
                .setText("Any number of target players each discard a number of cards equal to the highest mana value among permanents you control.")
        );
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
    }

    private CabalConditioning(final CabalConditioning card) {
        super(card);
    }

    @Override
    public CabalConditioning copy() {
        return new CabalConditioning(this);
    }
}
