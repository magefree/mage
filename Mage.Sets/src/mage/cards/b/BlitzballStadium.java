package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class BlitzballStadium extends CardImpl {

    public BlitzballStadium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{U}");

        // When this artifact enters, support X.
        Ability supportAbility = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("support X. <i>(Put a +1/+1 counter on each of up to X target creatures.)</i>")
        );
        supportAbility.addTarget(new TargetCreaturePermanent(0, 1));
        supportAbility.setTargetAdjuster(new XTargetsCountAdjuster());
        this.addAbility(supportAbility);

        // Go for the Goal! -- {3}, {T}: Until end of turn, target creature gains "Whenever this creature deals combat damage to a player, draw a card for each kind of counter on it" and it can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(BlitzballStadiumValue.instance))
        ).setText("Until end of turn, target creature gains \"Whenever this creature deals " +
                "combat damage to a player, draw a card for each kind of counter on it\""), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CantBeBlockedTargetEffect().setText("and it can't be blocked this turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Go for the Goal!"));
    }

    private BlitzballStadium(final BlitzballStadium card) {
        super(card);
    }

    @Override
    public BlitzballStadium copy() {
        return new BlitzballStadium(this);
    }
}

enum BlitzballStadiumValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = sourceAbility.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return 0;
        }
        return permanent.getCounters(game).size();
    }

    @Override
    public BlitzballStadiumValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "kind of counter on it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
