
package mage.cards.a;

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
 * @author nantuko
 */
public final class AngelsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Empyrial Archangel");
    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("a green creature");
    private static final FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent("a white creature");
    private static final FilterControlledCreaturePermanent filterBlue = new FilterControlledCreaturePermanent("a blue creature");

    static {
        filter.add(new NamePredicate("Empyrial Archangel"));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public AngelsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{W}, {tap}, Sacrifice a green creature, a white creature, and a blue creature:
        // Search your library for a card named Empyrial Archangel and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(1, 1, new FilterCard(filter))),
                new ManaCostsImpl("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterGreen, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterWhite, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterBlue, false)));
        this.addAbility(ability);
    }

    public AngelsHerald(final AngelsHerald card) {
        super(card);
    }

    @Override
    public AngelsHerald copy() {
        return new AngelsHerald(this);
    }
}
