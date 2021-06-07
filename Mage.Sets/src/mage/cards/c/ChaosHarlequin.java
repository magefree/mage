
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class ChaosHarlequin extends CardImpl {

    public ChaosHarlequin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {R}: Exile the top card of your library. If that card is a land card, Chaos Harlequin gets -4/-0 until end of turn. Otherwise, Chaos Harlequin gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChaosHarlequinEffect(), new ManaCostsImpl("{R}")));
    }

    private ChaosHarlequin(final ChaosHarlequin card) {
        super(card);
    }

    @Override
    public ChaosHarlequin copy() {
        return new ChaosHarlequin(this);
    }
}

class ChaosHarlequinEffect extends OneShotEffect {

    public ChaosHarlequinEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library. If that card is a land card, {this} gets -4/-0 until end of turn. Otherwise, {this} gets +2/+0 until end of turn";
    }

    public ChaosHarlequinEffect(final ChaosHarlequinEffect effect) {
        super(effect);
    }

    @Override
    public ChaosHarlequinEffect copy() {
        return new ChaosHarlequinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                player.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
                if (card.isLand(game)) {
                    game.addEffect(new BoostSourceEffect(-4, 0, Duration.EndOfTurn), source);
                } else {
                    game.addEffect(new BoostSourceEffect(2, 0, Duration.EndOfTurn), source);
                }
            }
            return true;
        }
        return false;
    }
}
