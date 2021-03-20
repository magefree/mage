package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrazedFirecat extends CardImpl {

    public CrazedFirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Crazed Firecat enters the battlefield, flip a coin until you lose a flip. Put a +1/+1 counter on Crazed Firecat for each flip you win.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CrazedFirecatEffect(), false));
    }

    private CrazedFirecat(final CrazedFirecat card) {
        super(card);
    }

    @Override
    public CrazedFirecat copy() {
        return new CrazedFirecat(this);
    }
}

class CrazedFirecatEffect extends OneShotEffect {

    CrazedFirecatEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin until you lose a flip. Put a +1/+1 counter on {this} for each flip you won.";
    }

    private CrazedFirecatEffect(final CrazedFirecatEffect effect) {
        super(effect);
    }

    @Override
    public CrazedFirecatEffect copy() {
        return new CrazedFirecatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (controller != null && sourceObject != null) {
            int flipsWon = 0;
            while (controller.flipCoin(source, game, true)) {
                flipsWon++;

                // AI workaround to stop on good condition
                if (controller.isComputer() && flipsWon >= 2) {
                    break;
                }
            }
            sourceObject.addCounters(CounterType.P1P1.createInstance(flipsWon), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }
}
