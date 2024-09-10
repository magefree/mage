package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class CleansingNova extends CardImpl {

    public CleansingNova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose one —
        // • Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));

        // • Destroy all artifacts and enchantments.
        Mode mode = new Mode(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS_AND_ENCHANTMENTS));
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
