package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TestOfTalents extends CardImpl {

    public TestOfTalents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target instant or sorcery spell. Search its controller's graveyard, hand, and library for any number of cards with the same name as that spell and exile them. That player shuffles, then draws a card for each card exiled from their hand this way.
        this.getSpellAbility().addEffect(new TestOfTalentsEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private TestOfTalents(final TestOfTalents card) {
        super(card);
    }

    @Override
    public TestOfTalents copy() {
        return new TestOfTalents(this);
    }
}

class TestOfTalentsEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    TestOfTalentsEffect() {
        super(false, "its controller's", "all cards with the same name as that spell");
        staticText = "counter target instant or sorcery spell. Search its controller's graveyard, hand, " +
                "and library for any number of cards with the same name as that spell and exile them. " +
                "That player shuffles, then draws a card for each card exiled from their hand this way";
    }

    private TestOfTalentsEffect(final TestOfTalentsEffect effect) {
        super(effect);
    }

    @Override
    public TestOfTalentsEffect copy() {
        return new TestOfTalentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
        if (stackObject == null) {
            return false;
        }
        MageObject targetObject = game.getObject(stackObject.getSourceId());
        String cardName;
        if (targetObject instanceof Card) {
            cardName = targetObject.getName();
        } else {
            cardName = "";
        }
        UUID searchPlayerId = stackObject.getControllerId();
        Player player = game.getPlayer(searchPlayerId);
        if (player == null) {
            return false;
        }
        int previousCount = player
                .getHand()
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .mapToInt(s -> CardUtil.haveSameNames(s, cardName) ? 1 : 0)
                .sum();
        game.getStack().counter(source.getFirstTarget(), source, game);
        this.applySearchAndExile(game, source, cardName, searchPlayerId);
        int newCount = player
                .getHand()
                .getCards(game)
                .stream()
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .mapToInt(s -> CardUtil.haveSameNames(s, cardName) ? 1 : 0)
                .sum();
        if (previousCount > newCount) {
            player.drawCards(previousCount - newCount, source, game);
        }
        return true;
    }
}
