package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class CoverOfWinter extends CardImpl {

    public CoverOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.supertype.add(SuperType.SNOW);

        // Cumulative upkeep {S}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{S}")));

        // If a creature would deal combat damage to you and/or one or more creatures you control, prevent X of that damage, where X is the number of age counters on Cover of Winter.
        this.addAbility(new SimpleStaticAbility(new CoverOfWinterEffect()));

        // {S}: Put an age counter on Cover of Winter.
        this.addAbility(new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.AGE.createInstance()), new ManaCostsImpl<>("{S}")));
    }

    private CoverOfWinter(final CoverOfWinter card) {
        super(card);
    }

    @Override
    public CoverOfWinter copy() {
        return new CoverOfWinter(this);
    }
}

class CoverOfWinterEffect extends PreventionEffectImpl {

    CoverOfWinterEffect() {
        super(Duration.WhileOnBattlefield, -1, true, false, new CountersSourceCount(CounterType.AGE));
        this.staticText = "If a creature would deal combat damage to you and/or one or more creatures you control, prevent X of that damage, where X is the number of age counters on {this}";
    }

    private CoverOfWinterEffect(final CoverOfWinterEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public CoverOfWinterEffect copy() {
        return new CoverOfWinterEffect(this);
    }
}
