package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NogginWhack extends CardImpl {

    public NogginWhack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{2}{B}{B}");
        this.subtype.add(SubType.ROGUE);


        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        // Target player reveals three cards from their hand. You choose two of them. That player discards those cards.
        this.getSpellAbility().addEffect(new NogginWhackEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private NogginWhack(final NogginWhack card) {
        super(card);
    }

    @Override
    public NogginWhack copy() {
        return new NogginWhack(this);
    }
}

class NogginWhackEffect extends OneShotEffect {

     NogginWhackEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player reveals three cards from their hand. You choose two of them. That player discards those cards";
    }

    private NogginWhackEffect(final NogginWhackEffect effect) {
        super(effect);
    }

    @Override
    public NogginWhackEffect copy() {
        return new NogginWhackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller == null || targetPlayer == null || sourceCard == null) {
            return false;
        }
        Cards cardsInHand = new CardsImpl();
        cardsInHand.addAll(targetPlayer.getHand());

        int count = Math.min(cardsInHand.size(), 3);

        TargetCard target = new TargetCard(count, Zone.HAND, new FilterCard());
        Cards revealedCards = new CardsImpl();

        if (targetPlayer.chooseTarget(Outcome.Discard, cardsInHand, target, source, game)) {
            List<UUID> targets = target.getTargets();
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    revealedCards.add(card);
                }
            }
        }

        int cardsToDiscard = Math.min(revealedCards.size(), 2);
        TargetCard targetInHand = new TargetCard(cardsToDiscard, cardsToDiscard, Zone.HAND, new FilterCard("card to discard"));

        if (!revealedCards.isEmpty()) {
            targetPlayer.revealCards(source, revealedCards, game);
            controller.chooseTarget(Outcome.Exile, revealedCards, targetInHand, source, game);
            targetPlayer.discard(new CardsImpl(targetInHand.getTargets()), false, source, game);
        }
        return true;
    }
}
