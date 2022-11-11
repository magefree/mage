package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class BraidssFrightfulReturn extends CardImpl {

    public BraidssFrightfulReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // Read ahead
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III, true);

        // I -- You may sacrifice a creature. If you do, each opponent discards a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new DoIfCostPaid(
                        new DiscardEachPlayerEffect(TargetController.OPPONENT),
                        new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
                )
        );

        // II -- Return target creature card from your graveyard to your hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new ReturnFromGraveyardToHandTargetEffect(),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );

        // III -- Target opponent may sacrifice a nonland, nontoken permanent. If they don't, they lose 2 life and you draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new BraidssFrightfulReturnEffect(),
                new TargetOpponent()
        );
        this.addAbility(sagaAbility);
    }

    private BraidssFrightfulReturn(final BraidssFrightfulReturn card) {
        super(card);
    }

    @Override
    public BraidssFrightfulReturn copy() {
        return new BraidssFrightfulReturn(this);
    }
}

class BraidssFrightfulReturnEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland, nontoken permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TokenPredicate.FALSE);
    }

    public BraidssFrightfulReturnEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Target opponent may sacrifice a nonland, nontoken permanent. If they don't, they lose 2 life and you draw a card.";
    }

    private BraidssFrightfulReturnEffect(final BraidssFrightfulReturnEffect effect) {
        super(effect);
    }

    @Override
    public BraidssFrightfulReturnEffect copy() {
        return new BraidssFrightfulReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
        boolean sacrifice = false;
        if (target.canChoose(opponent.getId(), source, game) &&
                opponent.chooseUse(outcome, "Sacrifice a nonland, nontoken permanent?", source, game)) {
            opponent.chooseTarget(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            sacrifice = permanent != null && permanent.sacrifice(source, game);
        }
        if (!sacrifice) {
            opponent.loseLife(2, game, source, false);
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
