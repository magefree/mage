package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrapyardRecombiner extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Construct card");
    private static final FilterControlledPermanent filter2
            = new FilterControlledArtifactPermanent("an artifact");

    static {
        filter.add(SubType.CONSTRUCT.getPredicate());
    }

    public ScrapyardRecombiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 2
        this.addAbility(new ModularAbility(this, 2));

        // {T}, Sacrifice an artifact: Search your library for a Construct card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    private ScrapyardRecombiner(final ScrapyardRecombiner card) {
        super(card);
    }

    @Override
    public ScrapyardRecombiner copy() {
        return new ScrapyardRecombiner(this);
    }
}
