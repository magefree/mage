package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author zeffirojoe
 */
public final class WildEndeavor extends CardImpl {

    public WildEndeavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Roll two d4 and choose one result. Create a number of 3/3 green Beast
        // creature tokens equal to that result. Then search your library for a number
        // of basic land cards equal to the other result, put them onto the battlefield
        // tapped, then shuffle.
        this.getSpellAbility().addEffect(new WildEndeavorEffect());
    }

    private WildEndeavor(final WildEndeavor card) {
        super(card);
    }

    @Override
    public WildEndeavor copy() {
        return new WildEndeavor(this);
    }
}

class WildEndeavorEffect extends OneShotEffect {

    WildEndeavorEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Roll two d4 and choose one result. " +
                "Create a number of 3/3 green Beast creature tokens equal to that result." +
                "Then search your library for a number of basic land cards " +
                "equal to the other result, put them onto the battlefield tapped, then shuffle.";
    }

    private WildEndeavorEffect(final WildEndeavorEffect effect) {
        super(effect);
    }

    @Override
    public WildEndeavorEffect copy() {
        return new WildEndeavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Integer> results = controller.rollDice(outcome, source, game, 4, 2, 0);
        int firstResult = results.get(0);
        int secondResult = results.get(1);
        int first, second;
        if (firstResult != secondResult && controller.chooseUse(outcome,
                "Choose the amount of 3/3 green Beast creature tokens.",
                "The other number is the amount of basic lands you search from your library and put on the battlefield tapped.",
                "" + firstResult, "" + secondResult, source, game)) {
            first = firstResult;
            second = secondResult;
        } else {
            first = secondResult;
            second = firstResult;
        }

        new BeastToken().putOntoBattlefield(first, game, source, source.getControllerId());

        TargetCardInLibrary target = new TargetCardInLibrary(0, second, StaticFilters.FILTER_CARD_BASIC_LAND);
        controller.searchLibrary(target, source, game);
        Set<Card> cards = target
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        controller.moveCards(
                cards, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        controller.shuffleLibrary(source, game);
        return true;
    }
}
