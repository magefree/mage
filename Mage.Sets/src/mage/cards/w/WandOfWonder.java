package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WandOfWonder extends CardImpl {

    public WandOfWonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        // {4}, {T}: Roll a d20. Each opponent exiles cards from the top of their library until they exile an instant or sorcery card, then shuffles the rest into their library. You may cast up to X instant and/or sorcery spells from among cards exiled this way without paying their mana costs.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(
                20, "roll a d20. Each opponent exiles cards from the top of their library " +
                "until they exile an instant or sorcery card, then shuffles the rest into their library. " +
                "You may cast up to X instant and/or sorcery spells from among cards exiled this way " +
                "without paying their mana costs"
        );

        // 1-9 | X is one
        effect.addTableEntry(1, 9, new WandOfWonderEffect(1));

        // 10-19 | X is two.
        effect.addTableEntry(10, 19, new WandOfWonderEffect(2));

        // 20 | X is three.
        effect.addTableEntry(20, 20, new WandOfWonderEffect(3));

        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WandOfWonder(final WandOfWonder card) {
        super(card);
    }

    @Override
    public WandOfWonder copy() {
        return new WandOfWonder(this);
    }
}

class WandOfWonderEffect extends OneShotEffect {

    private final int xValue;

    WandOfWonderEffect(int xValue) {
        super(Outcome.Benefit);
        staticText = "X is " + CardUtil.numberToText(xValue);
        this.xValue = xValue;
    }

    private WandOfWonderEffect(final WandOfWonderEffect effect) {
        super(effect);
        this.xValue = effect.xValue;
    }

    @Override
    public WandOfWonderEffect copy() {
        return new WandOfWonderEffect(this);
    }

    // {4}, {T}: Roll a d20. Each opponent exiles cards from the top of their library until
    // they exile an instant or sorcery card, then shuffles the rest into their library.
    // You may cast up to X instant and/or sorcery spells from among cards exiled this way without paying their mana costs.
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Cards toCast = new CardsImpl();
        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            for (Card card : opponent.getLibrary().getCards(game)) {
                opponent.moveCards(card, Zone.EXILED, source, game);
                if (card.isInstantOrSorcery(game)) {
                    toCast.add(card);
                    break;
                } else {
                    cards.add(card);
                }
            }
            opponent.putCardsOnBottomOfLibrary(cards, game, source, false);
            opponent.shuffleLibrary(source, game);
            cards.clear();
        }
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, toCast,
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, xValue
        );
        return true;
    }
}
