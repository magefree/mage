package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderingArchaic extends ModalDoubleFacedCard {

    public WanderingArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR}, "{5}",
                "Explore the Vastlands",
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{3}"
        );

        // 1.
        // Wandering Archaic
        // Creature - Avatar
        this.getLeftHalfCard().setPT(4, 4);

        // Whenever an opponent casts an instant or sorcery spell, they may pay {2}. If they don't, you may copy that spell. You may choose new targets for the copy.
        this.getLeftHalfCard().addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new WanderingArchaicEffect(),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.PLAYER
        ));

        // 2.
        // Explore the Vastlands
        // Sorcery
        // Each player looks at the top five cards of their library, reveals a land card and/or an instant or sorcery card from among them, then puts the cards they revealed this way into their hand and the rest on the bottom of their library in a random order. Each player gains 3 life.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExploreTheVastlandsEffect());
    }

    private WanderingArchaic(final WanderingArchaic card) {
        super(card);
    }

    @Override
    public WanderingArchaic copy() {
        return new WanderingArchaic(this);
    }
}

class WanderingArchaicEffect extends OneShotEffect {

    WanderingArchaicEffect() {
        super(Outcome.Benefit);
        staticText = "they may pay {2}. If they don't, you may copy that spell. You may choose new targets for the copy";
    }

    private WanderingArchaicEffect(final WanderingArchaicEffect effect) {
        super(effect);
    }

    @Override
    public WanderingArchaicEffect copy() {
        return new WanderingArchaicEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || opponent == null || spell == null) {
            return false;
        }
        Cost cost = ManaUtil.createManaCost(2, false);
        if (cost.canPay(source, source, opponent.getId(), game)
                && opponent.chooseUse(outcome, "Pay {2}?", source, game)
                && cost.pay(source, game, source, opponent.getId(), false)) {
            return true;
        }
        if (controller.chooseUse(outcome, "Copy " + spell.getName() + "?", source, game)) {
            spell.createCopyOnStack(game, source, controller.getId(), true);
        }
        return true;
    }
}

class ExploreTheVastlandsEffect extends OneShotEffect {

    ExploreTheVastlandsEffect() {
        super(Outcome.Benefit);
        staticText = "each player looks at the top five cards of their library " +
                "and may reveal a land card and/or an instant or sorcery card from among them. " +
                "Each player puts the cards they revealed this way into their hand and the rest " +
                "on the bottom of their library in a random order. Each player gains 3 life";
    }

    private ExploreTheVastlandsEffect(final ExploreTheVastlandsEffect effect) {
        super(effect);
    }

    @Override
    public ExploreTheVastlandsEffect copy() {
        return new ExploreTheVastlandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 5));
            TargetCard target = new ExploreTheVastlandsTarget();
            player.choose(outcome, cards, target, source, game);
            Cards toHand = new CardsImpl(target.getTargets());
            cards.removeIf(target.getTargets()::contains);
            player.revealCards(source, toHand, game);
            player.moveCards(toHand, Zone.HAND, source, game);
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.gainLife(3, game, source);
            }
        }
        return true;
    }
}

class ExploreTheVastlandsTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a land card and/or an instant or sorcery card");

    static {
        filter.add(Predicates.or(
                CardType.LAND.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.INSTANT.getPredicate()
        ));
    }

    ExploreTheVastlandsTarget() {
        super(0, 2, filter);
    }

    private ExploreTheVastlandsTarget(final ExploreTheVastlandsTarget target) {
        super(target);
    }

    @Override
    public ExploreTheVastlandsTarget copy() {
        return new ExploreTheVastlandsTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        boolean isLand = card.isLand(game);
        return this.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .noneMatch(c -> card.isLand(game) && c.isLand(game) || card.isInstantOrSorcery(game) && c.isInstantOrSorcery(game));
    }
}
