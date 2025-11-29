package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
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
public final class GraveyardTrespasser extends TransformingDoubleFacedCard {

    public GraveyardTrespasser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{B}",
                "Graveyard Glutton",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "B"
        );

        // Graveyard Trespasser
        this.getLeftHalfCard().setPT(3, 3);

        // Ward—Discard a card.
        this.getLeftHalfCard().addAbility(new WardAbility(new DiscardCardCost(), false));

        // Whenever Graveyard Trespasser enters the battlefield or attacks, exile up to one target card from a graveyard. If a creature card was exiled this way, each opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GraveyardTrespasserEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Graveyard Glutton
        this.getRightHalfCard().setPT(4, 4);

        // Ward—Discard a card.
        this.getRightHalfCard().addAbility(new WardAbility(new DiscardCardCost(), false));

        // Whenever Graveyard Glutton enters the battlefield or attacks, exile up to two target cards from graveyards. For each creature card exiled this way, each opponent loses 1 life and you gain 1 life.
        Ability backAbility = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GraveyardGluttonEffect());
        backAbility.addTarget(new TargetCardInGraveyard(0, 2));
        this.getRightHalfCard().addAbility(backAbility);

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private GraveyardTrespasser(final GraveyardTrespasser card) {
        super(card);
    }

    @Override
    public GraveyardTrespasser copy() {
        return new GraveyardTrespasser(this);
    }
}

class GraveyardTrespasserEffect extends OneShotEffect {

    GraveyardTrespasserEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target card from a graveyard. " +
                "If a creature card was exiled this way, each opponent loses 1 life and you gain 1 life";
    }

    private GraveyardTrespasserEffect(final GraveyardTrespasserEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardTrespasserEffect copy() {
        return new GraveyardTrespasserEffect(this);
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
        game.processAction();
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
