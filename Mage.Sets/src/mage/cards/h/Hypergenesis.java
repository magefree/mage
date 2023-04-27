
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class Hypergenesis extends CardImpl {

    public Hypergenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"");
        this.color.setGreen(true);

        // Suspend 3-{1}{G}{G}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{G}{G}"), this));

        // Starting with you, each player may put an artifact, creature, enchantment, or land card from their hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.
        this.getSpellAbility().addEffect(new HypergenesisEffect());
    }

    private Hypergenesis(final Hypergenesis card) {
        super(card);
    }

    @Override
    public Hypergenesis copy() {
        return new Hypergenesis(this);
    }
}

@SuppressWarnings("unchecked")
class HypergenesisEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an artifact, creature, enchantment, or land card");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate(), CardType.ENCHANTMENT.getPredicate(), CardType.LAND.getPredicate()));
    }

    HypergenesisEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may put an artifact, creature, enchantment, or land card from their hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.";
    }

    HypergenesisEffect(final HypergenesisEffect effect) {
        super(effect);
    }

    @Override
    public HypergenesisEffect copy() {
        return new HypergenesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(filter);

            while (controller.canRespond()) {
                if (currentPlayer != null && currentPlayer.canRespond() && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    if (firstInactivePlayer == null) {
                        firstInactivePlayer = currentPlayer.getId();
                    }
                    target.clearChosen();
                    if (target.canChoose(currentPlayer.getId(), source, game)
                            && currentPlayer.chooseUse(outcome, "Put card from your hand to play?", source, game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                                firstInactivePlayer = null;
                            }
                        }
                    }
                }
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
