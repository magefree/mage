package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Human01Token;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class TheCityOnTheEdgeOfForever extends CardImpl {

    public TheCityOnTheEdgeOfForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- All artifacts and creatures phase out. Create a 0/1 white Human creature token with "Permanents can't phase in."
        Effects effects = new Effects();
        effects.add(new PhaseOutArtifactsAndCreaturesEffect());
        effects.add(new CreateTokenEffect(new Human01Token()));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I, effects
        );

        // II -- You draw two cards and each player loses 2 life.
        Effects effects2 = new Effects();
        effects2.add(new DrawCardSourceControllerEffect(2));
        effects2.add(new LoseLifeAllPlayersEffect(2).concatBy("and"));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II, effects2
        );

        // III -- Sacrifice a creature token. If you do, you gain 5 life.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            new DoIfCostPaid(
                new GainLifeEffect(5), null,
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE), false
            )
        );

        this.addAbility(sagaAbility);
    }

    private TheCityOnTheEdgeOfForever(final TheCityOnTheEdgeOfForever card) {
        super(card);
    }

    @Override
    public TheCityOnTheEdgeOfForever copy() {
        return new TheCityOnTheEdgeOfForever(this);
    }
}

class PhaseOutArtifactsAndCreaturesEffect extends OneShotEffect {

    PhaseOutArtifactsAndCreaturesEffect() {
        super(Outcome.Detriment);
        this.staticText = "all artifacts and creatures phase out";
    }

    private PhaseOutArtifactsAndCreaturesEffect(final PhaseOutArtifactsAndCreaturesEffect effect) {
        super(effect);
    }

    @Override
    public PhaseOutArtifactsAndCreaturesEffect copy() {
        return new PhaseOutArtifactsAndCreaturesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> permIds = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE, source.getControllerId(), source, game)) {
            permIds.add(permanent.getId());
        }
        return new PhaseOutAllEffect(permIds).apply(game, source);
    }
}
