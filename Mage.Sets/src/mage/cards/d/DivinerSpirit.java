
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DivinerSpirit extends CardImpl {

    public DivinerSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Diviner Spirit deals combat damage to a player, you and that player each draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DivinerSpiritEffect(), false, true));
    }

    private DivinerSpirit(final DivinerSpirit card) {
        super(card);
    }

    @Override
    public DivinerSpirit copy() {
        return new DivinerSpirit(this);
    }
}

class DivinerSpiritEffect extends OneShotEffect {

    public DivinerSpiritEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you and that player each draw that many cards";
    }

    private DivinerSpiritEffect(final DivinerSpiritEffect effect) {
        super(effect);
    }

    @Override
    public DivinerSpiritEffect copy() {
        return new DivinerSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (sourceController != null && damagedPlayer != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                sourceController.drawCards(amount, source, game);
                damagedPlayer.drawCards(amount, source, game);
                return true;
            }
        }
        return false;
    }
}
