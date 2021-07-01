package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class DwarfholdChampion extends CardImpl {

    public DwarfholdChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As long as Dwarfhold Champion is equipped, it gets +0/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
                EquippedSourceCondition.instance,
                "As long as {this} is equipped, it gets +0/+2"
        )));
    }

    private DwarfholdChampion(final DwarfholdChampion card) {
        super(card);
    }

    @Override
    public DwarfholdChampion copy() {
        return new DwarfholdChampion(this);
    }
}
