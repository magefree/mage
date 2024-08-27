package mage.cards.d;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.mana.UntilEndOfTurnManaEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyDancer extends CardImpl {

    public DeadlyDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Deadly Dancer, add {R}{R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new TransformIntoSourceTriggeredAbility(new UntilEndOfTurnManaEffect(Mana.RedMana(2))));

        // {R}{R}: Deadly Dancer and another target creature each get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this}"), new ManaCostsImpl<>("{R}{R}"));
        ability.addEffect(new BoostTargetEffect(1, 0)
                .setText("and another target creature each get +1/+0 until end of turn"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private DeadlyDancer(final DeadlyDancer card) {
        super(card);
    }

    @Override
    public DeadlyDancer copy() {
        return new DeadlyDancer(this);
    }
}
