package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class FeldonsCane extends CardImpl {

    public FeldonsCane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {tap}, Exile Feldon's Cane: Shuffle your graveyard into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleYourGraveyardIntoLibraryEffect(), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private FeldonsCane(final FeldonsCane card) {
        super(card);
    }

    @Override
    public FeldonsCane copy() {
        return new FeldonsCane(this);
    }
}
