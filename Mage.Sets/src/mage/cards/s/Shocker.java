package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author markedagain
 */
public final class Shocker extends CardImpl {

    public Shocker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Shocker deals damage to a player, that player discards all the cards in their hand, then draws that many cards.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new ShockerEffect(), false, true));
    }

    private Shocker(final Shocker card) {
        super(card);
    }

    @Override
    public Shocker copy() {
        return new Shocker(this);
    }
}

class ShockerEffect extends OneShotEffect {

    ShockerEffect() {
        super(Outcome.Discard);
        this.staticText = " that player discards all the cards in their hand, then draws that many cards";
    }

    private ShockerEffect(final ShockerEffect effect) {
        super(effect);
    }

    @Override
    public ShockerEffect copy() {
        return new ShockerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        int count = targetPlayer.discard(targetPlayer.getHand(), false, source, game).size();
        targetPlayer.drawCards(count, source, game);
        return true;
    }
}
