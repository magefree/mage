package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RiseFall extends SplitCard {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public RiseFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}", "{B}{R}", SpellAbilityType.SPLIT);

        // Rise
        // Return target creature card from a graveyard and target creature on the battlefield to their owners' hands.
        getLeftHalfCard().getSpellAbility().addEffect(new RiseEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Fall
        // Target player reveals two cards at random from their hand, then discards each nonland card revealed this way.
        getRightHalfCard().getSpellAbility().addEffect(new FallEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    private RiseFall(final RiseFall card) {
        super(card);
    }

    @Override
    public RiseFall copy() {
        return new RiseFall(this);
    }
}

class RiseEffect extends OneShotEffect {

    RiseEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature card from a graveyard and target creature on the battlefield to their owners' hands";
    }

    private RiseEffect(final RiseEffect effect) {
        super(effect);
    }

    @Override
    public RiseEffect copy() {
        return new RiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cardsToHand = new CardsImpl();
        Card cardInGraveyard = game.getCard(getTargetPointer().getFirst(game, source));
        if (cardInGraveyard != null) {
            cardsToHand.add(cardInGraveyard);
        }
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            cardsToHand.add(permanent);
        }
        controller.moveCards(cardsToHand, Zone.HAND, source, game);
        return true;
    }
}

class FallEffect extends OneShotEffect {

    FallEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals two cards at random from their hand, then discards each nonland card revealed this way";
    }

    private FallEffect(final FallEffect effect) {
        super(effect);
    }

    @Override
    public FallEffect copy() {
        return new FallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null) {
            return false;
        }
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            return true;
        }
        if (targetPlayer.getHand().isEmpty()) {
            return true;
        }
        Card card = targetPlayer.getHand().getRandom(game);
        if (card == null) {
            return false;
        }
        Cards cards = new CardsImpl(card);
        if (targetPlayer.getHand().size() > 1) {
            do {
                card = targetPlayer.getHand().getRandom(game);
                if (card == null) {
                    return false;
                }
            } while (cards.contains(card.getId()));
            cards.add(card);
        }
        targetPlayer.revealCards(sourceObject.getIdName(), cards, game);
        targetPlayer.discard(new CardsImpl(cards.getCards(StaticFilters.FILTER_CARD_NON_LAND, game)), false, source, game);
        return true;
    }
}
