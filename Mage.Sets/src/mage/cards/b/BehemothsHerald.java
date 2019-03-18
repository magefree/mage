
package mage.cards.b;

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
public final class BehemothsHerald extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Godsire");
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent("a red creature");
    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("a green creature");
    private static final FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent("a white creature");

    static {
        filter.add(new NamePredicate("Godsire"));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BehemothsHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}, Sacrifice a red creature, a green creature, and a white creature:
        // Search your library for a card named Godsire and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, new FilterCard(filter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(target),
                new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterRed, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterGreen, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterWhite, false)));
        this.addAbility(ability);
    }

    public BehemothsHerald(final BehemothsHerald card) {
        super(card);
    }

    @Override
    public BehemothsHerald copy() {
        return new BehemothsHerald(this);
    }
}
