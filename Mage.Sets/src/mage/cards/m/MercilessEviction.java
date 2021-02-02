
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;

/**
 *
 * @author LevelX2
 */
public final class MercilessEviction extends CardImpl {

    public MercilessEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{B}");

        // Choose one - Exile all artifacts
        this.getSpellAbility().addEffect(new ExileAllEffect(new FilterArtifactPermanent("artifacts")));
        // or exile all creatures
        Mode mode = new Mode();
        mode.addEffect(new ExileAllEffect(FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addMode(mode);
        // or exile all enchantments
        Mode mode2 = new Mode();
        mode2.addEffect(new ExileAllEffect(new FilterEnchantmentPermanent("enchantments")));
        this.getSpellAbility().addMode(mode2);
        // or exile all planeswalkers.
        Mode mode3 = new Mode();
        mode3.addEffect(new ExileAllEffect(new FilterPlaneswalkerPermanent("planeswalkers")));
        this.getSpellAbility().addMode(mode3);
    }

    private MercilessEviction(final MercilessEviction card) {
        super(card);
    }

    @Override
    public MercilessEviction copy() {
        return new MercilessEviction(this);
    }
}
