package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootwiseSurvivor extends CardImpl {

    public RootwiseSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Survival -- At the beginning of your second main phase, if Rootwise Survivor is tapped, put three +1/+1 counters on up to one target land you control. That land becomes a 0/0 Elemental creature in addition to its other types. It gains haste until your next turn.
        Ability ability = new SurvivalAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)));
        ability.addEffect(new BecomesCreatureTargetEffect(new CreatureToken(
                0, 0, "0/0 Elemental creature"
        ).withSubType(SubType.ELEMENTAL), false, false, Duration.EndOfTurn));
        ability.addTarget(new TargetLandPermanent(0, 1));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("it gains haste until your next turn"));
        this.addAbility(ability);
    }

    private RootwiseSurvivor(final RootwiseSurvivor card) {
        super(card);
    }

    @Override
    public RootwiseSurvivor copy() {
        return new RootwiseSurvivor(this);
    }
}
