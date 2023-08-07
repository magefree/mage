package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KelsienThePlague extends CardImpl {

    public KelsienThePlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Kelsien, the Plague gets +1/+1 for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                KelsienThePlagueCount.instance, KelsienThePlagueCount.instance,
                Duration.WhileOnBattlefield, false
        )));

        // {T}: Kelsien deals 1 damage to target creature you don't control. When that creature dies this turn, you get an experience counter.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU)
        )));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private KelsienThePlague(final KelsienThePlague card) {
        super(card);
    }

    @Override
    public KelsienThePlague copy() {
        return new KelsienThePlague(this);
    }
}

enum KelsienThePlagueCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount = player.getCounters().getCount(CounterType.EXPERIENCE);
        }
        return amount;
    }

    @Override
    public KelsienThePlagueCount copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "experience counter you have";
    }
}
