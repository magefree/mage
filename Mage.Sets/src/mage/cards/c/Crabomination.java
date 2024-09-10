package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Crabomination extends CardImpl {

    public Crabomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Emerge from artifact {5}{B}{B}
        this.addAbility(new EmergeAbility(this, "{5}{B}{B}", StaticFilters.FILTER_PERMANENT_ARTIFACT, "from artifact"));

        // When Crabomination enters the battlefield, target opponent exiles the top card of their library, a card at random from their graveyard, and a card at random from their hand. You may cast a spell from among cards exiled this way without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CrabominationEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Crabomination(final Crabomination card) {
        super(card);
    }

    @Override
    public Crabomination copy() {
        return new Crabomination(this);
    }
}

class CrabominationEffect extends OneShotEffect {

    CrabominationEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles the top card of their library, "
                + "a card at random from their graveyard, "
                + "and a card at random from their hand. "
                + "You may cast a spell from among cards exiled this way without paying its mana cost";
    }

    private CrabominationEffect(final CrabominationEffect effect) {
        super(effect);
    }

    @Override
    public CrabominationEffect copy() {
        return new CrabominationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card fromTop = opponent.getLibrary().getFromTop(game);
        if (fromTop != null) {
            cards.add(fromTop);
        }
        Card fromGraveyard = opponent.getGraveyard().getRandom(game);
        if (fromGraveyard != null) {
            cards.add(fromGraveyard);
        }
        Card fromHand = opponent.getHand().getRandom(game);
        if (fromHand != null) {
            cards.add(fromHand);
        }
        opponent.moveCardsToExile(cards.getCards(game), source, game, true, null, "");
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castSpellWithAttributesForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }

}
