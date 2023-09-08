
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class AgelessEntity extends CardImpl {

    public AgelessEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you gain life, put that many +1/+1 counters on Ageless Entity.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AgelessEntityEffect(), false, true));
    }

    private AgelessEntity(final AgelessEntity card) {
        super(card);
    }

    @Override
    public AgelessEntity copy() {
        return new AgelessEntity(this);
    }
}

class AgelessEntityEffect extends OneShotEffect {

    public AgelessEntityEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that many +1/+1 counters on {this}";
    }

    private AgelessEntityEffect(final AgelessEntityEffect effect) {
        super(effect);
    }

    @Override
    public AgelessEntityEffect copy() {
        return new AgelessEntityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lifeGained = (Integer) this.getValue("gainedLife");
        if (lifeGained > 0) {
            return new AddCountersSourceEffect(CounterType.P1P1.createInstance(lifeGained)).apply(game, source);
        }
        return false;
    }
}
