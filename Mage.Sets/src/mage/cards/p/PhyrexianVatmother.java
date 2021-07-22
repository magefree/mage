

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Viserion
 */
public final class PhyrexianVatmother extends CardImpl {

    public PhyrexianVatmother (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new PoisonControllerEffect()));
    }

    public PhyrexianVatmother (final PhyrexianVatmother card) {
        super(card);
    }

    @Override
    public PhyrexianVatmother copy() {
        return new PhyrexianVatmother(this);
    }

}

class PoisonControllerEffect extends OneShotEffect {

    public PoisonControllerEffect() {
        super(Outcome.Damage);
        staticText = "you get a poison counter";

    }

    public PoisonControllerEffect(final PoisonControllerEffect effect) {
        super(effect);
    }

    @Override
    public PoisonControllerEffect copy() {
        return new PoisonControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.addCounters(CounterType.POISON.createInstance(), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

}
