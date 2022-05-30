
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Wehk
 */
public final class FadeFromMemory extends CardImpl {

    public FadeFromMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Exile target card from a graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        // Cycling {B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));
    }

    private FadeFromMemory(final FadeFromMemory card) {
        super(card);
    }

    @Override
    public FadeFromMemory copy() {
        return new FadeFromMemory(this);
    }
}
