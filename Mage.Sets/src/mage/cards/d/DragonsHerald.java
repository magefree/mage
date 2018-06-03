
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
public final class DragonsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Hellkite Overlord");
    private static final FilterControlledCreaturePermanent filterBlack = new FilterControlledCreaturePermanent("a black creature");
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent("a red creature");
    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("a green creature");

    static {
        filter.add(new NamePredicate("Hellkite Overlord"));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public DragonsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}, {tap}, Sacrifice a black creature, a red creature, and a green creature:
        // Search your library for a card named Hellkite Overlord and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, new FilterCard(filter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(target),
                new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterBlack, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterRed, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterGreen, false)));
        this.addAbility(ability);
    }

    public DragonsHerald(final DragonsHerald card) {
        super(card);
    }

    @Override
    public DragonsHerald copy() {
        return new DragonsHerald(this);
    }
}
