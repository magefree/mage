
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class EverbarkShaman extends CardImpl {

    private static final FilterCard filterForest = new FilterCard("Forest cards");
    private static final FilterCard filterTreefolk = new FilterCard("Treefolk card from your graveyard");

    static {
        filterForest.add(SubType.FOREST.getPredicate());
        filterTreefolk.add(SubType.TREEFOLK.getPredicate());
    }

    public EverbarkShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {T}, Exile a Treefolk card from your graveyard: Search your library for up to two Forest cards and put them onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filterForest), true, Outcome.PutLandInPlay), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filterTreefolk)));
        this.addAbility(ability);
    }

    private EverbarkShaman(final EverbarkShaman card) {
        super(card);
    }

    @Override
    public EverbarkShaman copy() {
        return new EverbarkShaman(this);
    }
}
