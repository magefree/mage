package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JaceArchitectOfThought extends CardImpl {

    public JaceArchitectOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(4);

        // +1: Until your next turn, whenever a creature an opponent controls attacks, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new AttacksAllTriggeredAbility(
                                new BoostTargetEffect(-1, 0, Duration.EndOfTurn)
                                        .setText("it gets -1/-0 until end of turn"),
                                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE,
                                SetTargetPointer.PERMANENT, false
                        )
                )
        ), 1));

        // -2: Reveal the top three cards of your library. An opponent separates those cards into two piles. 
        // Put one pile into your hand and the other on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new RevealAndSeparatePilesEffect(
                3, TargetController.OPPONENT, TargetController.YOU, Zone.LIBRARY
        ), -2));

        // -8: For each player, search that player's library for a nonland card and exile it, 
        // then that player shuffles their library. You may cast those cards without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new JaceArchitectOfThoughtEffect3(), -8));
    }

    private JaceArchitectOfThought(final JaceArchitectOfThought card) {
        super(card);
    }

    @Override
    public JaceArchitectOfThought copy() {
        return new JaceArchitectOfThought(this);
    }
}

class JaceArchitectOfThoughtEffect3 extends OneShotEffect {

    public JaceArchitectOfThoughtEffect3() {
        super(Outcome.PlayForFree);
        this.staticText = "For each player, search that player's library for a nonland card and exile it, "
                + "then that player shuffles. You may cast those cards without paying their mana costs";
    }

    private JaceArchitectOfThoughtEffect3(final JaceArchitectOfThoughtEffect3 effect) {
        super(effect);
    }

    @Override
    public JaceArchitectOfThoughtEffect3 copy() {
        return new JaceArchitectOfThoughtEffect3(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.chooseUse(Outcome.Benefit, "Look at all players' libraries before card select?", source, game)) {
            game.informPlayers(controller.getLogName() + " is looking at all players' libraries.");
            controller.lookAtAllLibraries(source, game);
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            TargetCardInLibrary target = new TargetCardInLibrary(
                    0, 1, StaticFilters.FILTER_CARD_A_NON_LAND
            );
            controller.searchLibrary(target, source, game, playerId);
            Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
            if (card == null) {
                continue;
            }
            cards.add(card);
            controller.moveCards(card, Zone.EXILED, source, game);
            player.shuffleLibrary(source, game);
        }
        cards.retainZone(Zone.EXILED, game);
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
