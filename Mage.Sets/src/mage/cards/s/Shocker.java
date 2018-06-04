
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author markedagain
 */
public final class Shocker extends CardImpl {

    public Shocker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Shocker deals damage to a player, that player discards all the cards in their hand, then draws that many cards.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new ShockerEffect(), false, true));
    }

    public Shocker(final Shocker card) {
        super(card);
    }

    @Override
    public Shocker copy() {
        return new Shocker(this);
    }
}
class ShockerEffect extends OneShotEffect {

    public ShockerEffect() {
        super(Outcome.Discard);
        this.staticText = " that player discards all the cards in their hand, then draws that many cards";
    }

    public ShockerEffect(final ShockerEffect effect) {
        super(effect);
    }

    @Override
    public ShockerEffect copy() {
        return new ShockerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                int count = targetPlayer.getHand().size();
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    targetPlayer.discard(card, source, game);
                }
                targetPlayer.drawCards(count, game);
                return false;
            }
        return true;
    }
}