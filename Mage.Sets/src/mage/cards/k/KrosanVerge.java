
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class KrosanVerge extends CardImpl {

    private static final FilterCard filterForest = new FilterCard("a Forest");
    private static final FilterCard filterPlains = new FilterCard("a Plains");

    static {
        filterForest.add(new SubtypePredicate(SubType.FOREST));
        filterPlains.add(new SubtypePredicate(SubType.PLAINS));
    }

    public KrosanVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Krosan Verge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {T}, Sacrifice Krosan Verge: Search your library for a Forest card and a Plains card and put them onto the battlefield tapped. Then shuffle your library.
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterForest), true, Outcome.PutLandInPlay);
        effect.setText("Search your library for a Forest card and a Plains card and put them onto the battlefield tapped. Then shuffle your library");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filterPlains), true, Outcome.PutLandInPlay);
        effect.setText(null);
        ability.addEffect(effect);
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public KrosanVerge(final KrosanVerge card) {
        super(card);
    }

    @Override
    public KrosanVerge copy() {
        return new KrosanVerge(this);
    }
}
