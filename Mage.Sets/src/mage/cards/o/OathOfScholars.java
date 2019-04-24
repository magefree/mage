
package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class OathOfScholars extends CardImpl {

    private final UUID originalId;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(new OathOfScholarsPredicate());
    }

    public OathOfScholars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // At the beginning of each player's upkeep, that player chooses target player who has more cards in hand than he or she does and is their opponent. The first player may discard their hand and draw three cards.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfScholarsEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
        originalId = ability.getOriginalId();

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, filter);
                target.setTargetController(activePlayer.getId());
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfScholars(final OathOfScholars card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public OathOfScholars copy() {
        return new OathOfScholars(this);
    }
}

class OathOfScholarsPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (targetPlayer == null
                || firstPlayer == null
                || !firstPlayer.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int countHandTargetPlayer = targetPlayer.getHand().size();
        int countHandFirstPlayer = firstPlayer.getHand().size();

        return countHandTargetPlayer > countHandFirstPlayer;
    }

    @Override
    public String toString() {
        return "player who has more cards in hand than he or she does";
    }
}

class OathOfScholarsEffect extends OneShotEffect {

    public OathOfScholarsEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "that player chooses target player who has more cards in hand than he or she does and is their opponent. The first player may discard their hand and draw three cards";
    }

    public OathOfScholarsEffect(OathOfScholarsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (sourceObject == null || firstPlayer == null) {
            return false;
        }
        if (firstPlayer.chooseUse(Outcome.AIDontUseIt, "Discard your hand and draw 3 cards?", source, game)) {
            firstPlayer.discard(firstPlayer.getHand().size(), true, source, game);
            firstPlayer.drawCards(3, game);
            return true;
        }
        return false;
    }

    @Override
    public OathOfScholarsEffect copy() {
        return new OathOfScholarsEffect(this);
    }
}
