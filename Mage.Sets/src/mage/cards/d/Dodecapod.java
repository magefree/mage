
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LoneFox
 */
public final class Dodecapod extends CardImpl {

    public Dodecapod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a spell or ability an opponent controls causes you to discard Dodecapod, put it onto the battlefield with two +1/+1 counters on it instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new DodecapodEffect()));

    }

    private Dodecapod(final Dodecapod card) {
        super(card);
    }

    @Override
    public Dodecapod copy() {
        return new Dodecapod(this);
    }
}


class DodecapodEffect extends DiscardOntoBattlefieldEffect {

    public DodecapodEffect() {
        super();
        staticText = "If a spell or ability an opponent controls causes you to discard {this}, put it onto the battlefield with two +1/+1 counters on it instead of putting it into your graveyard";
    }

    private DodecapodEffect(final DodecapodEffect effect) {
        super(effect);
    }

    @Override
    public DodecapodEffect copy() {
        return new DodecapodEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(super.replaceEvent(event, source, game)) {
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).apply(game, source);
            return true;
        }
        return false;
    }
}
