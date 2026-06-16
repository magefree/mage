package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FirebirdBlazingRanger extends CardImpl {

    public FirebirdBlazingRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Firebird attacks, other attacking creatures get +X/+0 until end of turn, where X is Firebird's power.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
            SourcePermanentPowerValue.NOT_NEGATIVE, StaticValue.get(0), Duration.EndOfTurn,
            StaticFilters.FILTER_ATTACKING_CREATURES, true
        ).setText("other attacking creatures get +X/+0 until end of turn, where X is {this}'s power")));
    }

    private FirebirdBlazingRanger(final FirebirdBlazingRanger card) {
        super(card);
    }

    @Override
    public FirebirdBlazingRanger copy() {
        return new FirebirdBlazingRanger(this);
    }
}
