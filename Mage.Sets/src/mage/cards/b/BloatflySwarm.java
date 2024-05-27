package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventDamageAndRemoveCountersEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BloatflySwarm extends CardImpl {

    public BloatflySwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Bloatfly Swarm enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"
        ));

        // If damage would be dealt to Bloatfly Swarm while it has a +1/+1 counter on it, prevent that damage, remove that many +1/+1 counters from it, then give each player a rad counter for each +1/+1 counter removed this way.
        this.addAbility(new SimpleStaticAbility(new BloatflySwarmPreventionEffect()), PreventDamageAndRemoveCountersEffect.createWatcher());
    }

    private BloatflySwarm(final BloatflySwarm card) {
        super(card);
    }

    @Override
    public BloatflySwarm copy() {
        return new BloatflySwarm(this);
    }
}

class BloatflySwarmPreventionEffect extends PreventDamageAndRemoveCountersEffect {

    BloatflySwarmPreventionEffect() {
        super(true, true, true);
        staticText += ", then give each player a rad counter for each +1/+1 counter removed this way";
    }

    private BloatflySwarmPreventionEffect(final BloatflySwarmPreventionEffect effect) {
        super(effect);
    }

    @Override
    public BloatflySwarmPreventionEffect copy() {
        return new BloatflySwarmPreventionEffect(this);
    }

    @Override
    protected void onDamagePrevented(GameEvent event, Ability source, Game game, int amountRemovedInTotal, int amountRemovedThisTime) {
        super.onDamagePrevented(event, source, game, amountRemovedInTotal, amountRemovedThisTime);
        new AddCountersPlayersEffect(CounterType.RAD.createInstance(amountRemovedThisTime), TargetController.EACH_PLAYER)
                .apply(game, source);
    }
}