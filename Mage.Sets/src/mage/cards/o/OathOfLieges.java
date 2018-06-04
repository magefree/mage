
package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class OathOfLieges extends CardImpl {

    private final UUID originalId;
    private static final FilterPlayer FILTER = new FilterPlayer("player who controls more lands than you do and is your opponent");

    static {
        FILTER.add(new OathOfLiegesPredicate());
    }

    public OathOfLieges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of each player's upkeep, that player chooses target player who controls more lands than he or she does and is their opponent. The first player may search their library for a basic land card, put that card onto the battlefield, then shuffle their library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new OathOfLiegesEffect(), TargetController.ANY, false);
        ability.addTarget(new TargetPlayer(1, 1, false, FILTER));
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Player activePlayer = game.getPlayer(game.getActivePlayerId());
            if (activePlayer != null) {
                ability.getTargets().clear();
                TargetPlayer target = new TargetPlayer(1, 1, false, FILTER);
                target.setTargetController(activePlayer.getId());
                ability.getTargets().add(target);
            }
        }
    }

    public OathOfLieges(final OathOfLieges card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public OathOfLieges copy() {
        return new OathOfLieges(this);
    }
}

class OathOfLiegesEffect extends OneShotEffect {

    public OathOfLiegesEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player chooses target player who controls more lands than he or she does and is their opponent. "
                + "The first player may search their library for a basic land card, put that card onto the battlefield, then shuffle their library";
    }

    public OathOfLiegesEffect(final OathOfLiegesEffect effect) {
        super(effect);
    }

    @Override
    public OathOfLiegesEffect copy() {
        return new OathOfLiegesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            if (activePlayer.chooseUse(outcome, "Search your library for a basic land card, put that card onto the battlefield, then shuffle your library?", source, game)) {
                Effect effect = new SearchLibraryPutInPlayTargetPlayerEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), false, true, Outcome.PutLandInPlay, true);
                effect.setTargetPointer(new FixedTarget(game.getActivePlayerId()));
                return effect.apply(game, source);
            }
            return true;
        }

        return false;
    }
}

class OathOfLiegesPredicate implements ObjectSourcePlayerPredicate<ObjectSourcePlayer<Player>> {

    private static final FilterLandPermanent FILTER = new FilterLandPermanent();

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        //Get active input.playerId because adjust target is used after canTarget function
        UUID activePlayerId = game.getActivePlayerId();
        if (targetPlayer == null || activePlayerId == null) {
            return false;
        }
        if (!targetPlayer.hasOpponent(activePlayerId, game)) {
            return false;
        }
        int countTargetPlayer = game.getBattlefield().countAll(FILTER, targetPlayer.getId(), game);
        int countActivePlayer = game.getBattlefield().countAll(FILTER, activePlayerId, game);

        return countTargetPlayer > countActivePlayer;
    }

    @Override
    public String toString() {
        return "player who controls more lands than he or she does and is their opponent";
    }
}
