package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MercilessEviction extends CardImpl {

    public MercilessEviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{B}");

        // Choose one - Exile all artifacts
        this.getSpellAbility().addEffect(new ExileAllEffect(new FilterArtifactPermanent("artifacts")));
        // or exile all creatures
        Mode mode = new Mode(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addMode(mode);
        // or exile all enchantments
        this.getSpellAbility().addMode(new Mode(new ExileAllEffect(new FilterEnchantmentPermanent("enchantments"))));
        // or exile all planeswalkers.
        this.getSpellAbility().addMode(new Mode(new ExileAllEffect(new FilterPlaneswalkerPermanent("planeswalkers"))));
    }

    private MercilessEviction(final MercilessEviction card) {
        super(card);
    }

    @Override
    public MercilessEviction copy() {
        return new MercilessEviction(this);
    }
}
