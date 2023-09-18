package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Ketsuban
 */
public class UrborgPanther extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature blocking {this}");
    private static final FilterControlledCreaturePermanent filter1
            = new FilterControlledCreaturePermanent("creature named Feral Shadow");
    private static final FilterControlledCreaturePermanent filter2
            = new FilterControlledCreaturePermanent("creature named Breathstealer");
    private static final FilterCard filterCard = new FilterCreatureCard("card named Spirit of the Night");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
        filter1.add(new NamePredicate("Feral Shadow"));
        filter2.add(new NamePredicate("Breathstealer"));
        filterCard.add(new NamePredicate("Spirit of the Night"));
    }

    public UrborgPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // B, Sacrifice Urborg Panther: Destroy target creature blocking Urborg Panther.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Sacrifice a creature named Feral Shadow, a creature named Breathstealer, and
        // Urborg Panther: Search your library for a card named Spirit of the Night and
        // put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                        1, 1, filterCard
                ), false, true),
                new CompositeCost(new CompositeCost(
                        new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter1)),
                        new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter2)),
                        ""
                ), new SacrificeSourceCost(), "sacrifice a creature named Feral Shadow, " +
                        "a creature named Breathstealer, and {this}")
        ));
    }

    private UrborgPanther(final UrborgPanther card) {
        super(card);
    }

    @Override
    public UrborgPanther copy() {
        return new UrborgPanther(this);
    }
}
