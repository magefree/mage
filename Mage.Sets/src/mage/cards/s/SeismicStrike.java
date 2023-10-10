
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class SeismicStrike extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public SeismicStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("{this} deals damage to target creature equal to the number of Mountains you control."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SeismicStrike(final SeismicStrike card) {
        super(card);
    }

    @Override
    public SeismicStrike copy() {
        return new SeismicStrike(this);
    }
}
