
package mage.cards.a;

import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public final class AetherBurst extends CardImpl {
    private static final FilterCard filter = new FilterCard("cards named Aether Burst");
    static {
        filter.add(new NamePredicate("Aether Burst"));
    }

    public AetherBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast this spell.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect().setText("Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast this spell"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new TargetsCountAdjuster(new IntPlusDynamicValue(1, new CardsInAllGraveyardsCount(filter))));
    }


    private AetherBurst(final AetherBurst card) {
        super(card);
    }

    @Override
    public AetherBurst copy() {
        return new AetherBurst(this);
    }
}
