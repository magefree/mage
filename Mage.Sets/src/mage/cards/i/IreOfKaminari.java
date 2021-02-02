
package mage.cards.i;

import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class IreOfKaminari extends CardImpl {

    private static final FilterCard filter = new FilterCard("Arcane");

    static {
        filter.add(SubType.ARCANE.getPredicate());
    }

    public IreOfKaminari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");
        this.subtype.add(SubType.ARCANE);

        // Ire of Kaminari deals damage to any target equal to the number of Arcane cards in your graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new CardsInControllerGraveyardCount(filter)));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private IreOfKaminari(final IreOfKaminari card) {
        super(card);
    }

    @Override
    public IreOfKaminari copy() {
        return new IreOfKaminari(this);
    }
}
