package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TripUp extends CardImpl {

    public TripUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target nonland permanent's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TripUp(final TripUp card) {
        super(card);
    }

    @Override
    public TripUp copy() {
        return new TripUp(this);
    }
}
