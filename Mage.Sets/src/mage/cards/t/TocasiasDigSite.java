package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TocasiasDigSite extends CardImpl {

    public TocasiasDigSite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TocasiasDigSite(final TocasiasDigSite card) {
        super(card);
    }

    @Override
    public TocasiasDigSite copy() {
        return new TocasiasDigSite(this);
    }
}
