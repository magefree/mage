package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ScytheSpecter extends CardImpl {

    public ScytheSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Scythe Specter deals combat damage to a player, each opponent discards a card. Each player who discarded a card with the highest converted mana cost among cards discarded this way loses life equal to that converted mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScytheSpecterEffect(), false));

    }

    private ScytheSpecter(final ScytheSpecter card) {
        super(card);
    }

    @Override
    public ScytheSpecter copy() {
        return new ScytheSpecter(this);
    }
}

class ScytheSpecterEffect extends OneShotEffect {

    ScytheSpecterEffect() {
        super(Outcome.Discard);
        this.staticText = "each opponent discards a card. Each player who discarded a card with the highest mana value among cards discarded this way loses life equal to that mana value";
    }

    private ScytheSpecterEffect(final ScytheSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> cardDiscarded = new HashMap<>();
        Map<UUID, Integer> discardedCheck = new HashMap<>();
        Integer highestCMC = 0;
        Integer currentCMC = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    Target target = new TargetDiscard(new FilterCard(), playerId);
                    opponent.chooseTarget(Outcome.Discard, target, source, game);
                    Card targetCard = game.getCard(target.getFirstTarget());
                    if (targetCard != null) {
                        currentCMC = targetCard.getManaValue();
                        if (highestCMC <= currentCMC) {
                            highestCMC = currentCMC;
                        }
                        cardDiscarded.put(playerId, targetCard);
                    }
                }
            }
            for (UUID opponentId : cardDiscarded.keySet()) {//discard must happen simultaneously
                Player player = game.getPlayer(opponentId);
                if (player != null
                        && player.discard(cardDiscarded.get(opponentId), false, source, game)) {
                    discardedCheck.put(opponentId, 1);//note that a card was discarded
                }
            }

            for (UUID playerId : game.getOpponents(controller.getId())) {//lose life equal to CMC
                Card card = cardDiscarded.get(playerId);
                if ((card != null) && (card.getManaValue() == highestCMC)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null
                            && discardedCheck.get(playerId) == 1) {//check that card was discarded
                        opponent.loseLife(highestCMC, game, source, false);
                    }
                }
            }
            cardDiscarded.clear();
            discardedCheck.clear();
            return true;
        }
        return false;
    }

    @Override
    public ScytheSpecterEffect copy() {
        return new ScytheSpecterEffect(this);
    }
}
