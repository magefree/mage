package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Backfir3
 */
public final class RainOfSalt extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("lands");

    public RainOfSalt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");

        // Destroy two target lands.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter));
    }

    private RainOfSalt(final RainOfSalt card) {
        super(card);
    }

    @Override
    public RainOfSalt copy() {
        return new RainOfSalt(this);
    }
}
