package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BringToLight extends CardImpl {

    public BringToLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{U}");

        // <i>Converge</i>-Search your library for a creature, instant, or sorcery card with converted mana
        // cost less than or equal to the number of colors of mana spent to cast Bring to Light, exile that card,
        // then shuffle your library. You may cast that card without paying its mana cost.
        this.getSpellAbility().addEffect(new BringToLightEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE);
    }

    private BringToLight(final BringToLight card) {
        super(card);
    }

    @Override
    public BringToLight copy() {
        return new BringToLight(this);
    }
}

class BringToLightEffect extends OneShotEffect {

    public BringToLightEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "search your library for a creature, instant, or sorcery card with mana value " +
                "less than or equal to the number of colors of mana spent to cast this spell, exile that card, "
                + "then shuffle. You may cast that card without paying its mana cost";
    }

    public BringToLightEffect(final BringToLightEffect effect) {
        super(effect);
    }

    @Override
    public BringToLightEffect copy() {
        return new BringToLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int numberColors = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
        FilterCard filter = new FilterCard("a creature, instant, or sorcery card with mana value "
                + "less than or equal to " + numberColors);
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, numberColors + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        controller.shuffleLibrary(source, game);
        CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
        return true;
    }
}
