
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThievingSprite extends CardImpl {

    public ThievingSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Thieving Sprite enters the battlefield, target player reveals X cards from their hand, where X is the number of Faeries you control.
        // You choose one of those cards. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThievingSpriteEffect(), false);
        TargetPlayer target = new TargetPlayer();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private ThievingSprite(final ThievingSprite card) {
        super(card);
    }

    @Override
    public ThievingSprite copy() {
        return new ThievingSprite(this);
    }
}

class ThievingSpriteEffect extends OneShotEffect {

    public ThievingSpriteEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals X cards from their hand, where X is the number of Faeries you control. You choose one of those cards. "
                + "That player discards that card";
    }

    public ThievingSpriteEffect(final ThievingSpriteEffect effect) {
        super(effect);
    }

    @Override
    public ThievingSpriteEffect copy() {
        return new ThievingSpriteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(SubType.FAERIE.getPredicate());
        int numberOfFaeries = game.getBattlefield().countAll(filter, controller.getId(), game);
        if (numberOfFaeries < 1) {
            return true;
        }

        Cards revealedCards = new CardsImpl();
        if (targetPlayer.getHand().size() > numberOfFaeries) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());

            TargetCard target = new TargetCard(numberOfFaeries, Zone.HAND, new FilterCard());

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
            targetPlayer.revealCards("Thieving Sprite", revealedCards, game);
            Card card = null;
            if (revealedCards.size() > 1) {
                controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                card = revealedCards.get(targetInHand.getFirstTarget(), game);
            } else {
                card = revealedCards.getRandom(game);
            }
            targetPlayer.discard(card, false, source, game);
        }
        return true;
    }
}
