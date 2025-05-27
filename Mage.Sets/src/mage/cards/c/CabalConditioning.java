
package mage.cards.c;

import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class CabalConditioning extends CardImpl {

    public CabalConditioning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Any number of target players each discard a number of cards equal to the greatest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS)
                .setText("Any number of target players each discard a number of cards equal to the greatest mana value among permanents you control.")
        );
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
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
