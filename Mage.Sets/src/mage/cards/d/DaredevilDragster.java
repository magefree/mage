
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class DaredevilDragster extends CardImpl {

    public DaredevilDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At end of combat, if Daredevil Dragster attacked or blocked this combat, put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EndOfCombatTriggeredAbility(new DaredevilDragsterEffect(), false),
                AttackedOrBlockedThisCombatSourceCondition.instance,
                "At end of combat, if {this} attacked or blocked this combat, put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards."),
                new AttackedOrBlockedThisCombatWatcher());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private DaredevilDragster(final DaredevilDragster card) {
        super(card);
    }

    @Override
    public DaredevilDragster copy() {
        return new DaredevilDragster(this);
    }
}

class DaredevilDragsterEffect extends OneShotEffect {

    public DaredevilDragsterEffect() {
        super(Outcome.Damage);
        this.staticText = "put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards";
    }

    private DaredevilDragsterEffect(final DaredevilDragsterEffect effect) {
        super(effect);
    }

    @Override
    public DaredevilDragsterEffect copy() {
        return new DaredevilDragsterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            permanent.addCounters(CounterType.VELOCITY.createInstance(), source.getControllerId(), source, game);
            if (permanent.getCounters(game).getCount(CounterType.VELOCITY) >= 2) {
                permanent.sacrifice(source, game);
                controller.drawCards(2, source, game);
            }
            return true;
        }
        return false;
    }
}
