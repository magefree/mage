package mage.cards.f;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author DominionSpy
 */
public final class FlotsamJetsam extends SplitCard {
    public FlotsamJetsam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{1}{G/U}", "{4}{U/B}{U/B}", SpellAbilityType.SPLIT);

        // Flotsam
        // Mill three cards. Investigate.
        getLeftHalfCard().getSpellAbility().addEffect(new MillCardsControllerEffect(3));
        getLeftHalfCard().getSpellAbility().addEffect(new InvestigateEffect());

        // Jetsam
        // Each opponent mills three cards, then you may cast a spell from each opponent's graveyard without paying its mana cost. If a spell cast this way would be put into a graveyard, exile it instead.
        getRightHalfCard().getSpellAbility().addEffect(new MillCardsEachPlayerEffect(3, TargetController.OPPONENT));
        getRightHalfCard().getSpellAbility().addEffect(new JetsamEffect().concatBy(", then "));

    }

    private FlotsamJetsam(final FlotsamJetsam card) {
        super(card);
    }

    @Override
    public FlotsamJetsam copy() {
        return new FlotsamJetsam(this);
    }
}

class JetsamEffect extends OneShotEffect {

    JetsamEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast a spell from each opponent's graveyard without paying its mana cost. " +
                "If a spell cast this way would be put into a graveyard, exile it instead.";
    }

    private JetsamEffect(final JetsamEffect effect) {
        super(effect);
    }

    @Override
    public JetsamEffect copy() {
        return new JetsamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Player> opponents = game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (controller == null || opponents.isEmpty()) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        for (Player opponent : opponents) {
            if (opponent.getGraveyard().count(StaticFilters.FILTER_CARD_NON_LAND, game) > 0) {
                TargetCard target = new TargetCardInOpponentsGraveyard(0, 1, StaticFilters.FILTER_CARD_NON_LAND, true);
                target.withNotTarget(true);
                if (controller.chooseTarget(outcome, opponent.getGraveyard(), target, source, game)) {
                    cards.add(game.getCard(target.getFirstTarget()));
                }
            }
        }

        if (!cards.isEmpty()) {
            cards
                    .stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .map(card -> new FixedTarget(card, game))
                    .forEach(target -> {
                        ContinuousEffect effect = new ThatSpellGraveyardExileReplacementEffect(false);
                        effect.setTargetPointer(target);
                        game.addEffect(effect, source);
                    });

            CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD_NON_LAND);
        }

        return true;
    }
}
