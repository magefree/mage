package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class GhostQuarter extends CardImpl {

    public GhostQuarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice Ghost Quarter: Destroy target land. Its controller may search
        // their library for a basic land card, put it onto the battlefield, then shuffle.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(false));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GhostQuarter(final GhostQuarter card) {
        super(card);
    }

    @Override
    public GhostQuarter copy() {
        return new GhostQuarter(this);
    }
}
