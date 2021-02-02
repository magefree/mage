package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class UnravelTheAether extends CardImpl {

    public UnravelTheAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose target artifact or enchantment. Its owner shuffles it into their library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private UnravelTheAether(final UnravelTheAether card) {
        super(card);
    }

    @Override
    public UnravelTheAether copy() {
        return new UnravelTheAether(this);
    }
}
