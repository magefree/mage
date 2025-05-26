package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RideTheShoopuf extends CardImpl {

    public RideTheShoopuf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Landfall - Whenever a land you control enters, put a +1/+1 counter on target creature you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {5}{G}{G}: This enchantment becomes a 7/7 Beast creature in addition to its other types.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(new CreatureToken(
                7, 7, "7/7 Beast creature", SubType.BEAST
        ), CardType.ENCHANTMENT, Duration.Custom).withKeepCreatureSubtypes(true), new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private RideTheShoopuf(final RideTheShoopuf card) {
        super(card);
    }

    @Override
    public RideTheShoopuf copy() {
        return new RideTheShoopuf(this);
    }
}
