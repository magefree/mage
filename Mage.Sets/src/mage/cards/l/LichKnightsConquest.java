package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class LichKnightsConquest extends CardImpl {

    public LichKnightsConquest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Sacrifice any number of artifacts, enchantments, and/or tokens. Return that many creature cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new LichKnightsConquestEffect());
    }

    private LichKnightsConquest(final LichKnightsConquest card) {
        super(card);
    }

    @Override
    public LichKnightsConquest copy() {
        return new LichKnightsConquest(this);
    }
}

class LichKnightsConquestEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, enchantments, and/or tokens");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    LichKnightsConquestEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice any number of artifacts, enchantments, and/or tokens. "
                + "Return that many creature cards from your graveyard to the battlefield.";
    }

    private LichKnightsConquestEffect(final LichKnightsConquestEffect effect) {
        super(effect);
    }

    @Override
    public LichKnightsConquestEffect copy() {
        return new LichKnightsConquestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }

        controller.chooseTarget(Outcome.Sacrifice, target, source, game);
        List<Permanent> toSacrifice = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!toSacrifice.isEmpty()) {
            int sacrificeCount = toSacrifice.size();
            game.informPlayers(controller.getLogName() + " chose " + sacrificeCount
                    + " permanents to sacrifice. " + CardUtil.getSourceLogName(game, source));

            new SacrificeTargetEffect()
                    .setTargetPointer(new FixedTargets(toSacrifice, game))
                    .apply(game, source);
            game.getState().processAction(game);

            int cardsToMove = Math.min(
                    sacrificeCount,
                    controller.getGraveyard()
                            .count(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, game)
            );

            target = new TargetCardInYourGraveyard(cardsToMove, cardsToMove, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return true;
            }

            controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game);
            Cards cards = new CardsImpl();
            cards.addAll(target.getTargets());

            controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        }

        return true;
    }

}