
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class IllusoryAmbusher extends CardImpl {

    public IllusoryAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Illusory Ambusher is dealt damage, draw that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new IllusoryAmbusherDealtDamageEffect(), false, false));
    }

    private IllusoryAmbusher(final IllusoryAmbusher card) {
        super(card);
    }

    @Override
    public IllusoryAmbusher copy() {
        return new IllusoryAmbusher(this);
    }
}

class IllusoryAmbusherDealtDamageEffect extends OneShotEffect {

    public IllusoryAmbusherDealtDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "draw that many cards";
    }

    public IllusoryAmbusherDealtDamageEffect(final IllusoryAmbusherDealtDamageEffect effect) {
        super(effect);
    }

    @Override
    public IllusoryAmbusherDealtDamageEffect copy() {
        return new IllusoryAmbusherDealtDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                player.drawCards(amount, source, game);
            }
            return true;
        }
        return false;
    }
}
