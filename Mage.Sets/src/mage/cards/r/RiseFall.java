
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RiseFall extends SplitCard {

    public RiseFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{B}", "{B}{R}", SpellAbilityType.SPLIT);

        // Rise
        // Return target creature card from a graveyard and target creature on the battlefield to their owners' hands.
        getLeftHalfCard().getSpellAbility().addEffect(new RiseEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Fall
        // Target player reveals two cards at random from their hand, then discards each nonland card revealed this way.
        getRightHalfCard().getSpellAbility().addEffect(new FallEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());
    }

    public RiseFall(final RiseFall card) {
        super(card);
    }

    @Override
    public RiseFall copy() {
        return new RiseFall(this);
    }
}

class RiseEffect extends OneShotEffect {

    public RiseEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature card from a graveyard and target creature on the battlefield to their owners' hands";
    }

    public RiseEffect(final RiseEffect effect) {
        super(effect);
    }

    @Override
    public RiseEffect copy() {
        return new RiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
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
        return false;
    }
}

class FallEffect extends OneShotEffect {

    public FallEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals two cards at random from their hand, then discards each nonland card revealed this way";
    }

    public FallEffect(final FallEffect effect) {
        super(effect);
    }

    @Override
    public FallEffect copy() {
        return new FallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                if (!targetPlayer.getHand().isEmpty()) {
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
                    for (Card cardToDiscard : cards.getCards(game)) {
                        if (!cardToDiscard.isLand()) {
                            targetPlayer.discard(cardToDiscard, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
