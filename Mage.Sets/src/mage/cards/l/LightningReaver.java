package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LightningReaver extends CardImpl {

    public LightningReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste; fear
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(FearAbility.getInstance());

        // Whenever Lightning Reaver deals combat damage to a player, put a charge counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), false));

        // At the beginning of your end step, Lightning Reaver deals damage equal to the number of charge counters on it to each opponent.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DamageOpponentsEffect(), TargetController.YOU, false));
    }

    private LightningReaver(final LightningReaver card) {
        super(card);
    }

    @Override
    public LightningReaver copy() {
        return new LightningReaver(this);
    }
}

class DamageOpponentsEffect extends OneShotEffect {

    public DamageOpponentsEffect() {
        super(Outcome.Damage);
        staticText = "Lightning Reaver deals damage equal to the number of charge counters on it to each opponent";
    }

    public DamageOpponentsEffect(final DamageOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue amount = new CountersSourceCount(CounterType.CHARGE);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(amount.calculate(game, source, this), source.getSourceId(), source, game);
            }
        }
        return true;
    }

    @Override
    public DamageOpponentsEffect copy() {
        return new DamageOpponentsEffect(this);
    }
}
