package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoverBlades extends CardImpl {

    public RoverBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Equip {4}
        this.addAbility(new EquipAbility(4));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private RoverBlades(final RoverBlades card) {
        super(card);
    }

    @Override
    public RoverBlades copy() {
        return new RoverBlades(this);
    }
}
