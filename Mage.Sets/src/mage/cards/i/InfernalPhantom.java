package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernalPhantom extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public InfernalPhantom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, Infernal Phantom gets +2/+0 until end of turn.
        this.addAbility(new EerieAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));

        // When Infernal Phantom dies, it deals damage equal to its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(xValue, "it")
                .setText("it deals damage equal to its power to any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private InfernalPhantom(final InfernalPhantom card) {
        super(card);
    }

    @Override
    public InfernalPhantom copy() {
        return new InfernalPhantom(this);
    }
}
