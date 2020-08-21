package mage.cards.b;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

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
    }

    public BringToLight(final BringToLight card) {
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
        this.staticText = "<i>Converge</i> &mdash; Search your library for a creature, instant, or sorcery card with converted mana "
                + "cost less than or equal to the number of colors of mana spent to cast {this}, exile that card, "
                + "then shuffle your library. You may cast that card without paying its mana cost";
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
        if (controller != null) {
            int numberColors = ColorsOfManaSpentToCastCount.getInstance().calculate(game, source, this);
            FilterCard filter = new FilterCard("a creature, instant, or sorcery card with converted mana "
                    + "cost less than or equal to " + numberColors);
            filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                    CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
            filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, numberColors + 1));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            controller.searchLibrary(target, source, game);
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
            controller.shuffleLibrary(source, game);
            if (card != null) {
                if (controller.chooseUse(Outcome.PlayForFree, "Cast " + card.getName()
                        + " without paying its mana cost?", source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                }
            }
            return true;
        }
        return false;
    }
}
