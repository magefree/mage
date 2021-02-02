
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class MindBurst extends CardImpl {
    
    private static final FilterCard filter = new FilterCard();
    
    static {
        filter.add(new NamePredicate("Mind Burst"));
    }

    public MindBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");
        

        // Target player discards X cards, where X is one plus the number of cards named Mind Burst in all graveyards.
        DynamicValue numberOfCardsNamedMindBurst = new IntPlusDynamicValue(1, new CardsInAllGraveyardsCount(filter));
        Effect effect = new DiscardTargetEffect(numberOfCardsNamedMindBurst);
        effect.setText("Target player discards X cards, where X is one plus the number of cards named Mind Burst in all graveyards.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
        
    }

    private MindBurst(final MindBurst card) {
        super(card);
    }

    @Override
    public MindBurst copy() {
        return new MindBurst(this);
    }
}
