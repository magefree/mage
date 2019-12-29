package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.ManaUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HollowSpecter extends CardImpl {

    public HollowSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hollow Specter deals combat damage to a player, you may pay {X}.
        // If you do, that player reveals X cards from their hand and you choose one of them. That player discards that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HollowSpecterEffect(), false, true));
    }

    public HollowSpecter(final HollowSpecter card) {
        super(card);
    }

    @Override
    public HollowSpecter copy() {
        return new HollowSpecter(this);
    }
}

class HollowSpecterEffect extends OneShotEffect {

    public HollowSpecterEffect() {
        super(Outcome.Discard);
        staticText = "you may pay {X}. If you do, that player reveals X cards from their hand and you choose one of them. That player discards that card";
    }

    public HollowSpecterEffect(final HollowSpecterEffect effect) {
        super(effect);
    }

    @Override
    public HollowSpecterEffect copy() {
        return new HollowSpecterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null && controller.chooseUse(Outcome.Benefit, "Do you want to to pay {X}?", source, game)) {
            int payCount = ManaUtil.playerPaysXGenericMana(true, "Hollow Specter", controller, source, game);
            if (payCount > 0) {
                // find to reveal
                Cards revealedCards = new CardsImpl();
                if (targetPlayer.getHand().size() > payCount) {
                    Cards cardsInHand = new CardsImpl();
                    cardsInHand.addAll(targetPlayer.getHand());
                    TargetCard target = new TargetCard(payCount, Zone.HAND, new FilterCard());
                    if (targetPlayer.choose(Outcome.Discard, cardsInHand, target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                revealedCards.add(card);
                            }
                        }
                    } else {
                        // take any cards on disconnect
                        targetPlayer.getHand().stream().limit(payCount).forEach(revealedCards::add);
                    }
                } else {
                    revealedCards.addAll(targetPlayer.getHand());
                }

                // select to discard
                TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                if (!revealedCards.isEmpty()) {
                    targetPlayer.revealCards("Hollow Specter", revealedCards, game);
                    Card card;
                    if (revealedCards.size() > 1) {
                        controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                        card = revealedCards.get(targetInHand.getFirstTarget(), game);
                    } else {
                        card = revealedCards.getRandom(game);
                    }

                    targetPlayer.discard(card, source, game);

                }
            }

            return true;
        }
        return false;
    }
}