package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.WrennAndSevenEmblem;
import mage.game.permanent.token.WrennAndSevenTreefolkToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennAndSeven extends CardImpl {

    public WrennAndSeven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRENN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // +1: Reveal the top four cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new LoyaltyAbility(new RevealLibraryPutIntoHandEffect(
                4, StaticFilters.FILTER_CARD_LANDS, Zone.GRAVEYARD
        ), 1));

        // 0: Put any number of land cards from your hand onto the battlefield tapped.
        this.addAbility(new LoyaltyAbility(new WrennAndSevenLandEffect(), 0));

        // −3: Create a green Treefolk creature token with reach and "This creature's power and toughness are each equal to the number of lands you control."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WrennAndSevenTreefolkToken()), -3));

        // −8: Return all permanent cards from your graveyard to your hand. You get an emblem with "You have no maximum hand size."
        Ability ability = new LoyaltyAbility(new WrennAndSevenReturnEffect(), -8);
        ability.addEffect(new GetEmblemEffect(new WrennAndSevenEmblem()));
        this.addAbility(ability);
    }

    private WrennAndSeven(final WrennAndSeven card) {
        super(card);
    }

    @Override
    public WrennAndSeven copy() {
        return new WrennAndSeven(this);
    }
}

class WrennAndSevenLandEffect extends OneShotEffect {

    WrennAndSevenLandEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "put any number of land cards from your hand onto the battlefield tapped";
    }

    private WrennAndSevenLandEffect(final WrennAndSevenLandEffect effect) {
        super(effect);
    }

    @Override
    public WrennAndSevenLandEffect copy() {
        return new WrennAndSevenLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().count(StaticFilters.FILTER_CARD_LAND, game) < 1) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS
        );
        player.choose(outcome, player.getHand(), target, game);
        return player.moveCards(
                new CardsImpl(target.getTargets()).getCards(game),
                Zone.BATTLEFIELD, source, game, true,
                false, true, null
        );
    }
}

class WrennAndSevenReturnEffect extends OneShotEffect {

    WrennAndSevenReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return all permanent cards from your graveyard to your hand.";
    }

    private WrennAndSevenReturnEffect(final WrennAndSevenReturnEffect effect) {
        super(effect);
    }

    @Override
    public WrennAndSevenReturnEffect copy() {
        return new WrennAndSevenReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                StaticFilters.FILTER_CARD_PERMANENT, game
        ), Zone.HAND, source, game);
    }
}
