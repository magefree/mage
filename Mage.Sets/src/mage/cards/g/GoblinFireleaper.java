package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinFireleaper extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public GoblinFireleaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Goblin Fireleaper gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        ));

        // When Goblin Fireleaper dies, it deals damage equal to its power to target creature an opponent controls.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(xValue)
                .setText("it deals damage equal to its power to target creature an opponent controls"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private GoblinFireleaper(final GoblinFireleaper card) {
        super(card);
    }

    @Override
    public GoblinFireleaper copy() {
        return new GoblinFireleaper(this);
    }
}
