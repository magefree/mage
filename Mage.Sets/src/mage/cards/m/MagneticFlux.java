

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 * @author Loki
 */
public final class MagneticFlux extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Artifact creatures");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MagneticFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter, false));
    }

    public MagneticFlux(final MagneticFlux card) {
        super(card);
    }

    @Override
    public MagneticFlux copy() {
        return new MagneticFlux(this);
    }

}
