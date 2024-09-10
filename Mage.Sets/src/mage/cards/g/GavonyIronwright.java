package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GavonyIronwright extends CardImpl {

    public GavonyIronwright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Fateful hour - As long as you have 5 or less life, other creatures you control get +1/+4.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostControlledEffect(1, 4, Duration.WhileOnBattlefield, true),
                FatefulHourCondition.instance, "<br><i>Fateful hour</i> &mdash; As long as you have 5 or less life, other creatures you control get +1/+4")));
    }

    private GavonyIronwright(final GavonyIronwright card) {
        super(card);
    }

    @Override
    public GavonyIronwright copy() {
        return new GavonyIronwright(this);
    }
}
