package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.token.RogueToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurrencyConverter extends CardImpl {

    public CurrencyConverter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever you discard a card, you may exile that card from your graveyard.
        this.addAbility(new DiscardCardControllerTriggeredAbility(new CurrencyConverterExileEffect(), true));

        // {2}, {T}: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}: Put a card exiled with Currency Converter into your graveyard. If it's a land card, create a Treasure token. If it's a nonland card, create a 2/2 black Rogue creature token.
        this.addAbility(new SimpleActivatedAbility(new CurrencyConverterTokenEffect(), new TapSourceCost()));
    }

    private CurrencyConverter(final CurrencyConverter card) {
        super(card);
    }

    @Override
    public CurrencyConverter copy() {
        return new CurrencyConverter(this);
    }
}

class CurrencyConverterExileEffect extends OneShotEffect {

    CurrencyConverterExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card from your graveyard";
    }

    private CurrencyConverterExileEffect(final CurrencyConverterExileEffect effect) {
        super(effect);
    }

    @Override
    public CurrencyConverterExileEffect copy() {
        return new CurrencyConverterExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = (Card) getValue("discardedCard");
        if (player == null || card == null || !card.isOwnedBy(player.getId())
                || !Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
            return false;
        }
        return player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}

class CurrencyConverterTokenEffect extends OneShotEffect {

    CurrencyConverterTokenEffect() {
        super(Outcome.Benefit);
        staticText = "put a card exiled with {this} into your graveyard. If it's a land card, " +
                "create a Treasure token. If it's a nonland card, create a 2/2 black Rogue creature token";
    }

    private CurrencyConverterTokenEffect(final CurrencyConverterTokenEffect effect) {
        super(effect);
    }

    @Override
    public CurrencyConverterTokenEffect copy() {
        return new CurrencyConverterTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Card card;
        if (exileZone.size() > 1) {
            TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
            player.choose(outcome, exileZone, target, game);
            card = exileZone.get(target.getFirstTarget(), game);
        } else {
            card = exileZone.getRandom(game);
        }
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.GRAVEYARD, source, game);
        Token token = card.isLand(game) ? new TreasureToken() : new RogueToken();
        token.putOntoBattlefield(1, game, source);
        return true;
    }
}
