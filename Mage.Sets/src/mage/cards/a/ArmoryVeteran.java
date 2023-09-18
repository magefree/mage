package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class ArmoryVeteran extends CardImpl {

    public ArmoryVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as Armory Veteran is equipped, it has menace.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility()),
                EquippedSourceCondition.instance,
                "As long as {this} is equipped, it has menace. " +
                        "<i>(It can't be blocked except by two or more creatures.)</i>"
        )));
    }

    private ArmoryVeteran(final ArmoryVeteran card) {
        super(card);
    }

    @Override
    public ArmoryVeteran copy() {
        return new ArmoryVeteran(this);
    }
}
