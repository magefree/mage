
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */

public final class KrazyKow extends CardImpl {

    public KrazyKow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.COW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, roll a six-sided die. If you a roll a 1, sacrifice Krazy Kow and it deals 3 damage to each creature and each player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new KrazyKowEffect(), TargetController.YOU, false));
    }

    private KrazyKow(final KrazyKow card) {
        super(card);
    }

    @Override
    public KrazyKow copy() {
        return new KrazyKow(this);
    }
}

class KrazyKowEffect extends OneShotEffect {
    KrazyKowEffect() {
        super(Outcome.Benefit);
        this.staticText = "roll a six-sided die. If you roll a 1, sacrifice {this} and it deals 3 damage to each creature and each player";
    }

    KrazyKowEffect(final KrazyKowEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int result = controller.rollDice(outcome, source, game, 6);
            if (result == 1) {
                new SacrificeSourceEffect().apply(game, source);
                return new DamageEverythingEffect(3).apply(game, source);
            }
        }
        return false;
    }

    @Override
    public KrazyKowEffect copy() {
        return new KrazyKowEffect(this);
    }
}