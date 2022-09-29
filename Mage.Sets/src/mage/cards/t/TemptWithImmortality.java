package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TemptWithImmortality extends CardImpl {

    public TemptWithImmortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Tempting offer - Return a creature card from your graveyard to the battlefield. Each opponent may return a creature card from their graveyard to the battlefield. For each opponent who does, return a creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new TemptWithImmortalityEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.TEMPTING_OFFER);
    }

    private TemptWithImmortality(final TemptWithImmortality card) {
        super(card);
    }

    @Override
    public TemptWithImmortality copy() {
        return new TemptWithImmortality(this);
    }
}

class TemptWithImmortalityEffect extends OneShotEffect {

    public TemptWithImmortalityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return a creature card from your graveyard to the battlefield. " +
                "Each opponent may return a creature card from their graveyard to the battlefield. " +
                "For each opponent who does, return a creature card from your graveyard to the battlefield";

    }

    public TemptWithImmortalityEffect(final TemptWithImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithImmortalityEffect copy() {
        return new TemptWithImmortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
            TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            target.setNotTarget(true);
            controller.choose(outcome, controller.getGraveyard(), target, game);
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.BATTLEFIELD, source, game);
        }

        int opponentsReturnedCreatures = 0;
        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null || opponent.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) < 1) {
                continue;
            }
            TargetCard targetCardOpponent = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            targetCardOpponent.setNotTarget(true);

            if (!opponent.chooseUse(outcome, "Return a creature card from your graveyard to the battlefield?", source, game)) {
                continue;
            }
            opponent.choose(outcome, opponent.getGraveyard(), targetCardOpponent, game);
            Card card = game.getCard(targetCardOpponent.getFirstTarget());
            if (card != null) {
                opponentsReturnedCreatures++;
                opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        opponentsReturnedCreatures = Math.min(
                opponentsReturnedCreatures,
                controller.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game)
        );
        if (opponentsReturnedCreatures > 0) {
            TargetCard target = new TargetCardInYourGraveyard(
                    opponentsReturnedCreatures, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
            );
            target.setNotTarget(true);
            controller.choose(outcome, controller.getGraveyard(), target, game);
            controller.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        }
        return true;

    }
}
