
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SquidToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ChasmSkulker extends CardImpl {

    public ChasmSkulker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SQUID);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));

        // When Chasm Skulker dies, create X 1/1 blue Squid creature tokens with islandwalk, where X is the number of +1/+1 counters on Chasm Skulker.
        this.addAbility(new DiesSourceTriggeredAbility(new ChasmSkulkerEffect(), false));
    }

    private ChasmSkulker(final ChasmSkulker card) {
        super(card);
    }

    @Override
    public ChasmSkulker copy() {
        return new ChasmSkulker(this);
    }
}

class ChasmSkulkerEffect extends OneShotEffect {

    public ChasmSkulkerEffect() {
        super(Outcome.Benefit);
        this.staticText = "create X 1/1 blue Squid creature tokens with islandwalk, where X is the number of +1/+1 counters on Chasm Skulker";
    }

    public ChasmSkulkerEffect(final ChasmSkulkerEffect effect) {
        super(effect);
    }

    @Override
    public ChasmSkulkerEffect copy() {
        return new ChasmSkulkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent != null) {
                int counters = permanent.getCounters(game).getCount(CounterType.P1P1);
                if (counters > 0) {
                    return new CreateTokenEffect(new SquidToken(), counters).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }
}
