

package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class Unicycle extends CardImpl {

    public Unicycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.VEHICLE);
        this.subtype.add(SubType.EQUIPMENT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Equipped creature has first strike and haste.
        Ability ability = new SimpleStaticAbility(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)
        );
        Effect effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(" and haste");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private Unicycle(final Unicycle card) {
        super(card);
    }

    @Override
    public Unicycle copy() {
        return new Unicycle(this);
    }

}
