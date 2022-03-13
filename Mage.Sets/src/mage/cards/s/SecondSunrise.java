package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SecondSunrise extends CardImpl {

    public SecondSunrise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");

        // Each player returns to the battlefield all artifact, creature, enchantment, and land cards in their graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new SecondSunriseEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private SecondSunrise(final SecondSunrise card) {
        super(card);
    }

    @Override
    public SecondSunrise copy() {
        return new SecondSunrise(this);
    }
}

class SecondSunriseEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    SecondSunriseEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Each player returns to the battlefield all artifact, creature, enchantment, " +
                "and land cards in their graveyard that were put there from the battlefield this turn.";
    }

    private SecondSunriseEffect(final SecondSunriseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            result |= player.moveCards(player.getGraveyard().getCards(
                    filter, source.getControllerId(), source, game
            ), Zone.BATTLEFIELD, source, game);
        }
        return result;
    }

    @Override
    public SecondSunriseEffect copy() {
        return new SecondSunriseEffect(this);
    }
}
