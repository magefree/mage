
package mage.cards.v;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExtortAbility;
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
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class VizkopaConfessor extends CardImpl {

    public VizkopaConfessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);


        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());

        // When Vizkopa Confessor enters the battlefield, pay any amount of life. Target opponent reveals that many cards from their hand. You choose one of them and exile it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VizkopaConfessorEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VizkopaConfessor(final VizkopaConfessor card) {
        super(card);
    }

    @Override
    public VizkopaConfessor copy() {
        return new VizkopaConfessor(this);
    }
}

class VizkopaConfessorEffect extends OneShotEffect {

    public VizkopaConfessorEffect() {
        super(Outcome.Benefit);
        this.staticText = "pay any amount of life. Target opponent reveals that many cards from their hand. You choose one of them and exile it";
    }

    private VizkopaConfessorEffect(final VizkopaConfessorEffect effect) {
        super(effect);
    }

    @Override
    public VizkopaConfessorEffect copy() {
        return new VizkopaConfessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && targetPlayer != null && sourceCard != null) {
            int payLife = controller.getAmount(0, controller.getLife(),"Pay how many life?", game);
            if (payLife > 0) {
                controller.loseLife(payLife, game, source, false);
                game.informPlayers(sourceCard.getName() + ": " + controller.getLogName() + " pays " + payLife + " life");

                Cards cardsInHand = new CardsImpl();
                cardsInHand.addAll(targetPlayer.getHand());

                int count = Math.min(cardsInHand.size(), payLife);

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

                TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to exile"));

                if (!revealedCards.isEmpty()) {
                    targetPlayer.revealCards(sourceCard.getName(), revealedCards, game);
                    controller.chooseTarget(Outcome.Exile, revealedCards, targetInHand, source, game);
                    Card card = revealedCards.get(targetInHand.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, null, source, game, Zone.HAND, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
