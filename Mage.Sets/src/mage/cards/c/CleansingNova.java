package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;

/**
 *
 * @author TheElk801
 */
public final class CleansingNova extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("artifacts and enchantments");

    public CleansingNova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose one —
        // • Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // • Destroy all artifacts and enchantments.
        Mode mode = new Mode(new DestroyAllEffect(filter));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private CleansingNova(final CleansingNova card) {
        super(card);
    }

    @Override
    public CleansingNova copy() {
        return new CleansingNova(this);
    }
}
