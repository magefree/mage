package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RomulanToken;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class InThePaleMoonlight extends CardImpl {

    public InThePaleMoonlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Each opponent sacrifices a nontoken creature of their choice.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new SacrificeOpponentsEffect(StaticFilters.FILTER_CREATURE_NON_TOKEN)
        );

        // II -- Investigate.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II, new InvestigateEffect()
        );

        // III -- You may sacrifice an artifact or creature. If you do, create two 2/2 black Romulan creature tokens.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            new DoIfCostPaid(
                new CreateTokenEffect(new RomulanToken(), 2),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
            )
        );

        this.addAbility(sagaAbility);
    }

    private InThePaleMoonlight(final InThePaleMoonlight card) {
        super(card);
    }

    @Override
    public InThePaleMoonlight copy() {
        return new InThePaleMoonlight(this);
    }
}
