package mage.cards.e;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ettercap extends AdventureCard {

    public Ettercap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIDER, SubType.BEAST}, "{4}{G}",
                "Web Shot",
                new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Ettercap
        this.getLeftHalfCard().setPT(2, 5);

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // Web Shot
        // Destroy target creature with flying.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        finalizeCard();
    }

    private Ettercap(final Ettercap card) {
        super(card);
    }

    @Override
    public Ettercap copy() {
        return new Ettercap(this);
    }
}
