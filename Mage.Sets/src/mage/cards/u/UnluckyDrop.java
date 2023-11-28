package mage.cards.u;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class UnluckyDrop extends CardImpl {

    public UnluckyDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target artifact or creature's owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(
                "target artifact or creature's owner puts it on the top or bottom of their library"
        ));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

    }

    private UnluckyDrop(final UnluckyDrop card) {
        super(card);
    }

    @Override
    public UnluckyDrop copy() {
        return new UnluckyDrop(this);
    }
}
