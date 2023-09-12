
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class LifebloodHydra extends CardImpl {

    public LifebloodHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{G}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifeblood Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Lifeblood Hydra dies, you gain life and draw cards equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(new LifebloodHydraEffect(), false));
    }

    private LifebloodHydra(final LifebloodHydra card) {
        super(card);
    }

    @Override
    public LifebloodHydra copy() {
        return new LifebloodHydra(this);
    }
}

class LifebloodHydraEffect extends OneShotEffect {

    public LifebloodHydraEffect() {
        super(Outcome.Benefit);
        this.staticText = "you gain life and draw cards equal to its power";
    }

    private LifebloodHydraEffect(final LifebloodHydraEffect effect) {
        super(effect);
    }

    @Override
    public LifebloodHydraEffect copy() {

        return new LifebloodHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent diedPermanent = (Permanent) getValue("permanentLeftBattlefield");
            if (diedPermanent != null) {
                controller.gainLife(diedPermanent.getPower().getValue(), game, source);
                controller.drawCards(diedPermanent.getPower().getValue(), source, game);
            }
            return true;
        }
        return false;
    }
}
