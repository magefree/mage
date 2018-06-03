
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class DemonsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Prince of Thralls");
    private static final FilterControlledCreaturePermanent filterBlue = new FilterControlledCreaturePermanent("a blue creature");
    private static final FilterControlledCreaturePermanent filterBlack = new FilterControlledCreaturePermanent("a black creature");
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent("a red creature");

    static {
        filter.add(new NamePredicate("Prince of Thralls"));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }

    public DemonsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {tap}, Sacrifice a blue creature, a black creature, and a red creature:
        // Search your library for a card named Prince of Thralls and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, new FilterCard(filter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(target),
                new ManaCostsImpl("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterBlue, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterBlack, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterRed, false)));
        this.addAbility(ability);
    }

    public DemonsHerald(final DemonsHerald card) {
        super(card);
    }

    @Override
    public DemonsHerald copy() {
        return new DemonsHerald(this);
    }
}
