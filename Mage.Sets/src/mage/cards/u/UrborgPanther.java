package mage.cards.u;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * 
 * @author Ketsuban
 */
public class UrborgPanther extends CardImpl {

    private static final FilterControlledCreaturePermanent filter1 = new FilterControlledCreaturePermanent("creature named Feral Shadow");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent("creature named Breathstealer");

    private static final FilterCard filterCard = new FilterCreatureCard("card named Spirit of the Night");

    static {
        filter1.add(new NamePredicate("Feral Shadow"));
        filter2.add(new NamePredicate("Breathstealer"));
        filterCard.add(new NamePredicate("Spirit of the Night"));
    }

    public UrborgPanther(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // B, Sacrifice Urborg Panther: Destroy target creature blocking Urborg Panther.
        Ability ability1 = new SimpleActivatedAbility(new DestroyTargetEffect(),
                new ColoredManaCost(ColoredManaSymbol.B));
        ability1.addCost(new SacrificeSourceCost());
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking {this}");
        filter.add(new BlockingAttackerIdPredicate(this.getId()));
        ability1.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability1);

        // Sacrifice a creature named Feral Shadow, a creature named Breathstealer, and
        // Urborg Panther: Search your library for a card named Spirit of the Night and
        // put that card onto the battlefield. Then shuffle your library.
        Ability ability2 = new SimpleActivatedAbility(
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(1, 1, new FilterCard(filterCard))),
            new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter1, true)));
        ability2.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter2, true)));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private UrborgPanther(final UrborgPanther card) {
        super(card);
    }

    @Override
    public UrborgPanther copy() {
        return new UrborgPanther(this);
    }

}
