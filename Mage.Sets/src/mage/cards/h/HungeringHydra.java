package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;

/**
 *
 * @author TheElk801
 */
public final class HungeringHydra extends CardImpl {

    public HungeringHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Hungering Hydra enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // Hungering Hydra can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedByMoreThanOneSourceEffect()
        ));

        // Whenever damage is dealt to Hungering Hydra, put that many +1/+1 counters on it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new HungeringHydraEffect(),
                false, false, true
        ));
    }

    public HungeringHydra(final HungeringHydra card) {
        super(card);
    }

    @Override
    public HungeringHydra copy() {
        return new HungeringHydra(this);
    }
}

class HungeringHydraEffect extends OneShotEffect {

    public HungeringHydraEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that many +1/+1 counters on it";
    }

    public HungeringHydraEffect(final HungeringHydraEffect effect) {
        super(effect);
    }

    @Override
    public HungeringHydraEffect copy() {
        return new HungeringHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (int) this.getValue("damage");
        if (damage == 0) {
            return false;
        }
        return new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(damage)
        ).apply(game, source);
    }
}
