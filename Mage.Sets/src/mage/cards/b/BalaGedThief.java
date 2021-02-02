
package mage.cards.b;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class BalaGedThief extends CardImpl {

    public BalaGedThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE, SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Bala Ged Thief or another Ally enters the battlefield under your control, target player reveals a number of cards from their hand equal to the number of Allies you control. You choose one of them. That player discards that card.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new BalaGedThiefEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private BalaGedThief(final BalaGedThief card) {
        super(card);
    }

    @Override
    public BalaGedThief copy() {
        return new BalaGedThief(this);
    }
}

class BalaGedThiefEffect extends OneShotEffect {

    public BalaGedThiefEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals a number of cards from their hand equal to the number of Allies you control. You choose one of them. That player discards that card";
    }

    public BalaGedThiefEffect(final BalaGedThiefEffect effect) {
        super(effect);
    }

    @Override
    public BalaGedThiefEffect copy() {
        return new BalaGedThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());

        if (targetPlayer == null) {
            return false;
        }

        Player you = game.getPlayer(source.getControllerId());

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(SubType.ALLY.getPredicate());

        int numberOfAllies = game.getBattlefield().countAll(filter, you.getId(), game);

        Cards cardsInHand = new CardsImpl();
        cardsInHand.addAll(targetPlayer.getHand());

        int count = Math.min(cardsInHand.size(), numberOfAllies);

        TargetCard target = new TargetCard(count, Zone.HAND, new FilterCard());
        Cards revealedCards = new CardsImpl();

        if (targetPlayer.choose(Outcome.DrawCard, cardsInHand, target, game)) {
            List<UUID> targets = target.getTargets();
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    revealedCards.add(card);
                }
            }
        }

        TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));

        if (!revealedCards.isEmpty()) {
            targetPlayer.revealCards("Bala Ged Thief", revealedCards, game);
            you.choose(Outcome.Neutral, revealedCards, targetInHand, game);
            Card card = revealedCards.get(targetInHand.getFirstTarget(), game);
            if (card != null) {
                targetPlayer.discard(card, false, source, game);
                game.informPlayers("Bala Ged Thief: " + targetPlayer.getLogName() + " discarded " + card.getName());
            }
        }
        return true;
    }
}
