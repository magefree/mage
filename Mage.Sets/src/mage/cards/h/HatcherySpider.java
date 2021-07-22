package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class HatcherySpider extends CardImpl {

    public HatcherySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Undergrowth — When you cast this spell, reveal the top X cards of your library, where X is the number of creature cards in your graveyard. You may put a green permanent card with converted mana cost X or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.addAbility(new CastSourceTriggeredAbility(
                new HatcherySpiderEffect(), false,
                "<i>Undergrowth</i> &mdash; "
        ));
    }

    private HatcherySpider(final HatcherySpider card) {
        super(card);
    }

    @Override
    public HatcherySpider copy() {
        return new HatcherySpider(this);
    }
}

class HatcherySpiderEffect extends OneShotEffect {

    public HatcherySpiderEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top X cards of your library, "
                + "where X is the number of creature cards in your graveyard. "
                + "You may put a green permanent card with mana value "
                + "X or less from among them onto the battlefield. "
                + "Put the rest on the bottom of your library "
                + "in a random order.";
    }

    public HatcherySpiderEffect(final HatcherySpiderEffect effect) {
        super(effect);
    }

    @Override
    public HatcherySpiderEffect copy() {
        return new HatcherySpiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = new CardsInControllerGraveyardCount(
                StaticFilters.FILTER_CARD_CREATURE
        ).calculate(game, source, this);
        FilterCard filter = new FilterPermanentCard(
                "green permanent card with mana value "
                + xValue + " or less"
        );
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN, xValue + 1
        ));
        TargetCard target = new TargetCardInLibrary(filter);
        Cards cards = new CardsImpl(
                player.getLibrary().getTopCards(game, xValue)
        );
        if (player.chooseUse(outcome, "Put a card onto the battlefield?", source, game)
                && player.choose(outcome, cards, target, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null
                    && player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                cards.remove(card);
            }
        }
        while (!cards.isEmpty()) {
            Card card = cards.getRandom(game);
            player.getLibrary().putOnBottom(card, game);
            cards.remove(card);
        }
        return true;
    }
}
