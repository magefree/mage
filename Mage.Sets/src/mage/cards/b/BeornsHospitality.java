package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class BeornsHospitality extends CardImpl {

    public BeornsHospitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on target creature you control.
        Ability ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {5}{G}{G}: This enchantment becomes a Bear creature in addition to its other types and gains "This creature's power and toughness are each equal to the number of lands you control."
        Ability gainedAbility = new SimpleStaticAbility(new SetBasePowerToughnessSourceEffect(
                LandsYouControlCount.instance, Duration.EndOfGame
        ).setText("this creature's power and toughness are each equal to the number of lands you control"));
        Ability ability2 = new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
            new CreatureToken(
                0, 0,
                "Bear creature",
                SubType.BEAR
            ),
            CardType.ENCHANTMENT,
            Duration.Custom
        ).withKeepCreatureSubtypes(true), new ManaCostsImpl<>("{5}{G}{G}"));
        ability2.addEffect(new GainAbilitySourceEffect(gainedAbility).setText("and gains \"" + gainedAbility.getRule() + "\""));
        this.addAbility(ability2);
    }

    private BeornsHospitality(final BeornsHospitality card) {
        super(card);
    }

    @Override
    public BeornsHospitality copy() {
        return new BeornsHospitality(this);
    }
}
