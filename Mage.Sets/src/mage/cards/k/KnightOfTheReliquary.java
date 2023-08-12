
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KnightOfTheReliquary extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("Forest or Plains");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(), SubType.PLAINS.getPredicate()));
    }

    public KnightOfTheReliquary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Knight of the Reliquary gets +1/+1 for each land card in your graveyard.
        CardsInControllerGraveyardCount value = new CardsInControllerGraveyardCount(new FilterLandCard());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));

        // {T}, Sacrifice a Forest or Plains: Search your library for a land card, put it onto the battlefield, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new TapSourceCost());
        costs.add(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(target, false), costs));
    }

    private KnightOfTheReliquary(final KnightOfTheReliquary card) {
        super(card);
    }

    @Override
    public KnightOfTheReliquary copy() {
        return new KnightOfTheReliquary(this);
    }
}
