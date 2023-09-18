package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author weirddan455
 */
public final class FlorianVoldarenScion extends CardImpl {

    public FlorianVoldarenScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your postcombat main phase, look at the top X cards of your library, where X is the total amount of life your opponents lost this turn.
        // Exile one of those cards and put the rest on the bottom of your library in a random order. You may play the exiled card this turn.
        this.addAbility(new BeginningOfPostCombatMainTriggeredAbility(new FlorianVoldarenScionEffect(), TargetController.YOU, false));
    }

    private FlorianVoldarenScion(final FlorianVoldarenScion card) {
        super(card);
    }

    @Override
    public FlorianVoldarenScion copy() {
        return new FlorianVoldarenScion(this);
    }
}

class FlorianVoldarenScionEffect extends OneShotEffect {

    public FlorianVoldarenScionEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top X cards of your library, where X is the total amount of life your opponents lost this turn. "
                + "Exile one of those cards and put the rest on the bottom of your library in a random order. You may play the exiled card this turn";
    }

    private FlorianVoldarenScionEffect(final FlorianVoldarenScionEffect effect) {
        super(effect);
    }

    @Override
    public FlorianVoldarenScionEffect copy() {
        return new FlorianVoldarenScionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (controller != null && watcher != null) {
            int lifeLost = watcher.getAllOppLifeLost(controller.getId(), game);
            if (lifeLost > 0) {
                Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, lifeLost));
                int numCards = cards.size();
                if (numCards > 0) {
                    controller.lookAtCards(source, null, cards, game);
                    Card selectedCard;
                    if (numCards == 1) {
                        selectedCard = game.getCard(cards.iterator().next());
                    } else {
                        TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD);
                        controller.chooseTarget(outcome, cards, target, source, game);
                        selectedCard = game.getCard(target.getFirstTarget());
                    }
                    if (selectedCard != null) {
                        cards.remove(selectedCard);
                        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                                game, source, selectedCard, TargetController.YOU, Duration.EndOfTurn, false, false, false
                        );
                    }
                    if (!cards.isEmpty()) {
                        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
