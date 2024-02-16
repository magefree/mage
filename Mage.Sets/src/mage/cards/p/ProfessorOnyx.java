package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProfessorOnyx extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(
            "a creature with the greatest power among creatures that player controls"
    );

    static {
        filter.add(ProfessorOnyxPredicate.instance);
    }

    public ProfessorOnyx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(5);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, each opponent loses 2 life and you gain 2 life.
        Ability ability = new MagecraftAbility(new LoseLifeOpponentsEffect(2), false);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);

        // +1: You lose 1 life. Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        ability = new LoyaltyAbility(new LoseLifeSourceControllerEffect(1), 1);
        ability.addEffect(new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.GRAVEYARD));
        this.addAbility(ability);

        // −3: Each opponent sacrifices a creature with the greatest power among creatures that player controls.
        this.addAbility(new LoyaltyAbility(new SacrificeOpponentsEffect(filter), -3));

        // −8: Each opponent may discard a card. If they don't, they lose 3 life. Repeat this process six more times.
        this.addAbility(new LoyaltyAbility(new ProfessorOnyxEffect(), -8));
    }

    private ProfessorOnyx(final ProfessorOnyx card) {
        super(card);
    }

    @Override
    public ProfessorOnyx copy() {
        return new ProfessorOnyx(this);
    }
}

enum ProfessorOnyxPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPower().getValue()
                >= game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        input.getControllerId(), game
                )
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(Integer.MIN_VALUE);
    }
}

class ProfessorOnyxEffect extends OneShotEffect {

    ProfessorOnyxEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may discard a card. If they don't, " +
                "they lose 3 life. Repeat this process six more times";
    }

    private ProfessorOnyxEffect(final ProfessorOnyxEffect effect) {
        super(effect);
    }

    @Override
    public ProfessorOnyxEffect copy() {
        return new ProfessorOnyxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (int i = 0; i < 6; i++) {
            Map<UUID, Card> playerMap = new HashMap<>();
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    continue;
                }
                TargetDiscard target = new TargetDiscard(
                        0, 1, StaticFilters.FILTER_CARD, playerId
                );
                player.choose(Outcome.Discard, target, source, game);
                playerMap.put(playerId, game.getCard(target.getFirstTarget()));
            }
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    continue;
                }
                if (playerMap.get(playerId) == null
                        || !player.discard(playerMap.get(playerId), false, source, game)) {
                    player.loseLife(3, game, source, false);
                }
            }
        }
        return true;
    }
}
