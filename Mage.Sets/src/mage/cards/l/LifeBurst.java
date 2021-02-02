
package mage.cards.l;

import java.util.UUID;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class LifeBurst extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("card named Life Burst");

    static {
        filter.add(new NamePredicate("Life Burst"));
    }

    public LifeBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target player gains 4 life, then gains 4 life for each card named Life Burst in each graveyard.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(4));
        Effect effect = new GainLifeTargetEffect(new MultipliedValue(new CardsInAllGraveyardsCount(filter), 4));
        effect.setText(", then gains 4 life for each card named {this} in each graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private LifeBurst(final LifeBurst card) {
        super(card);
    }

    @Override
    public LifeBurst copy() {
        return new LifeBurst(this);
    }
}
