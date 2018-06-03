
package mage.cards.h;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
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
        if (targetPlayer != null && controller != null && controller.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", source, game)) {
            int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            Cost cost = new GenericManaCost(costX);
            int amountToReveal = costX;
            Cards revealedCards = new CardsImpl();
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                if (amountToReveal > 0 && targetPlayer.getHand().size() > amountToReveal) {
                    Cards cardsInHand = new CardsImpl();
                    cardsInHand.addAll(targetPlayer.getHand());
                    TargetCard target = new TargetCard(amountToReveal, Zone.HAND, new FilterCard());
                    if (targetPlayer.choose(Outcome.Discard, cardsInHand, target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                revealedCards.add(card);
                            }
                        }
                    }
                } else {
                    revealedCards.addAll(targetPlayer.getHand());
                }
                TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                if (!revealedCards.isEmpty()) {
                    targetPlayer.revealCards("Hollow Specter", revealedCards, game);
                    Card card = null;
                    if(revealedCards.size() > 1) {
                        controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                        card = revealedCards.get(targetInHand.getFirstTarget(), game);
                    } else {
                        card = revealedCards.getRandom(game);
                    }
                    if (card != null) {
                        targetPlayer.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}