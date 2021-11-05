package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraveyardGlutton extends CardImpl {

    public GraveyardGlutton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Ward—Discard a card.
        this.addAbility(new WardAbility(new DiscardCardCost()));

        // Whenever Graveyard Glutton enters the battlefield or attacks, exile up to two target cards from graveyards. For each creature card exiled this way, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GraveyardGluttonEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 2));
        this.addAbility(ability);

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private GraveyardGlutton(final GraveyardGlutton card) {
        super(card);
    }

    @Override
    public GraveyardGlutton copy() {
        return new GraveyardGlutton(this);
    }
}

class GraveyardGluttonEffect extends OneShotEffect {

    GraveyardGluttonEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to two target cards from graveyards. " +
                "For each creature card exiled this way, each opponent loses 1 life and you gain 1 life";
    }

    private GraveyardGluttonEffect(final GraveyardGluttonEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardGluttonEffect copy() {
        return new GraveyardGluttonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        int amount = cards.count(StaticFilters.FILTER_CARD_CREATURE, game);
        if (amount < 1) {
            return true;
        }
        player.gainLife(amount, game, source);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(amount, game, source, false);
            }
        }
        return true;
    }
}
