package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplitTheSpoils extends CardImpl {

    public SplitTheSpoils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Exile up to five target permanent cards from your graveyard and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new SplitTheSpoilsEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 5, StaticFilters.FILTER_CARD_PERMANENT
        ));
    }

    private SplitTheSpoils(final SplitTheSpoils card) {
        super(card);
    }

    @Override
    public SplitTheSpoils copy() {
        return new SplitTheSpoils(this);
    }
}

class SplitTheSpoilsEffect extends OneShotEffect {

    SplitTheSpoilsEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to five target permanent cards from your graveyard and separate them into two piles. " +
                "An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard";
    }

    private SplitTheSpoilsEffect(final SplitTheSpoilsEffect effect) {
        super(effect);
    }

    @Override
    public SplitTheSpoilsEffect copy() {
        return new SplitTheSpoilsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        TargetCard target = new TargetCardInExile(0, 5, StaticFilters.FILTER_CARD, null);
        target.withChooseHint("To put in pile 1").setNotTarget(true);
        player.choose(outcome, cards, target, source, game);
        List<Card> pile1 = new ArrayList<>();
        target.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(pile1::add);
        List<Card> pile2 = new ArrayList<>();
        cards.removeIf(target.getTargets()::contains);
        pile2.addAll(cards.getCards(game));
        TargetOpponent targetOpponent = new TargetOpponent();
        targetOpponent.setNotTarget(true);
        player.choose(outcome, targetOpponent, source, game);
        if (game.getPlayer(targetOpponent.getFirstTarget()).choosePile(
                outcome, "Choose a pile to go to hand (the other goes to graveyard)", pile1, pile2, game
        )) {
            player.moveCards(new CardsImpl(pile1), Zone.HAND, source, game);
            player.moveCards(new CardsImpl(pile2), Zone.GRAVEYARD, source, game);
        } else {
            player.moveCards(new CardsImpl(pile2), Zone.HAND, source, game);
            player.moveCards(new CardsImpl(pile1), Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
