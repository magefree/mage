package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.AlienRhinoToken;
import mage.game.permanent.token.Human11WithWard2Token;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;


/**
 *
 * @author padfoothelix
 */
public final class FugitiveOfTheJudoon extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Doctor card");
    private static final FilterControlledPermanent filterHuman = new FilterControlledPermanent(SubType.HUMAN,"a Human you control"); 
    private static final FilterControlledArtifactPermanent filterArtifact = new FilterControlledArtifactPermanent("an artifact you control"); 

    static {
	filter.add(SubType.DOCTOR.getPredicate());
    }

    public FugitiveOfTheJudoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

	// I -- Create a 1/1 white Human creature token with ward {2} and a 4/4 white Alien Rhino creature token.
	sagaAbility.addChapterEffect(
		this, SagaChapter.CHAPTER_I,
		new CreateTokenEffect(new Human11WithWard2Token()).withAdditionalTokens(new AlienRhinoToken())
	);

	// II -- Investigate.
	sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new InvestigateEffect());

        // III -- You may exile a Human you control and an artifact you control. If you do, search your library for a Doctor card, put it onto the battlefield, then shuffle.
	sagaAbility.addChapterEffect(
		this, SagaChapter.CHAPTER_III,
		new DoIfCostPaid(
			new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)),
			new CompositeCost(
				new ExileTargetCost(new TargetControlledPermanent(1, 1, filterHuman, true)), 
				new ExileTargetCost(new TargetControlledPermanent(1, 1, filterArtifact, true)),
				"exile a Human you control and an artifact you control"
			),
			"Exile a Human and an artifact ?"
		)	
	);

        this.addAbility(sagaAbility);

    }

    private FugitiveOfTheJudoon(final FugitiveOfTheJudoon card) {
        super(card);
    }

    @Override
    public FugitiveOfTheJudoon copy() {
        return new FugitiveOfTheJudoon(this);
    }
}
