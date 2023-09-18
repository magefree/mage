package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class BarbedShocker extends CardImpl {

    public BarbedShocker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Barbed Shocker deals damage to a player, that player discards all the cards in their hand, then draws that many cards.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(
                new BarbedShockerEffect(), false, true
        ));
    }

    private BarbedShocker(final BarbedShocker card) {
        super(card);
    }

    @Override
    public BarbedShocker copy() {
        return new BarbedShocker(this);
    }
}

class BarbedShockerEffect extends OneShotEffect {

    BarbedShockerEffect() {
        super(Outcome.Discard);
        this.staticText = "that player discards all the cards in their hand, then draws that many cards";
    }

    private BarbedShockerEffect(final BarbedShockerEffect effect) {
        super(effect);
    }

    @Override
    public BarbedShockerEffect copy() {
        return new BarbedShockerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer == null || targetPlayer.getHand().isEmpty()) {
            return false;
        }
        targetPlayer.drawCards(targetPlayer.discard(
                targetPlayer.getHand(), false, source, game
        ).size(), source, game);
        return true;
    }
}