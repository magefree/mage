package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class TheRuinousPowers extends CardImpl {

    public TheRuinousPowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}");
        

        // At the beginning of your upkeep, choose an opponent at random. Exile the top card of that player's library.
        // Until end of turn, you may play that card and you may spend mana as though it were mana of any color to cast it.
        // When you cast a spell this way, its owner loses life equal to its mana value.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new TheRuinousPowersEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TheRuinousPowers(final TheRuinousPowers card) {
        super(card);
    }

    @Override
    public TheRuinousPowers copy() {
        return new TheRuinousPowers(this);
    }
}

// Based on YouFindSomePrisonersEffect
class TheRuinousPowersEffect extends OneShotEffect {

    TheRuinousPowersEffect() {
        super(Outcome.Benefit);
        staticText = "Exile the top card of that player’s library. Until end of turn, you may play that card and you may spend mana as though it were mana of any color to cast it.";
    }

    private TheRuinousPowersEffect(final TheRuinousPowersEffect effect) {
        super(effect);
    }

    @Override
    public TheRuinousPowersEffect copy() {
        return new TheRuinousPowersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null) {
            return false;
        }
        Card card = opponent.getLibrary().getFromTop(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (card != null) {
            CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, true);
        }
        return true;
    }
}
