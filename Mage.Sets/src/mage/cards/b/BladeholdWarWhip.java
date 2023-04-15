package mage.cards.b;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author @stwalsh4118
 */
public final class BladeholdWarWhip extends CardImpl {

    public BladeholdWarWhip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equip abilities you activate of other Equipment cost {1} less to activate.
        Ability ability = new SimpleStaticAbility(
            new AbilitiesCostReductionControllerEffect(
                    EquipAbility.class, "Equip", 1, true
            ).setText("Equip abilities you activate of other Equipment cost {1} less to activate."));
        this.addAbility(ability);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Equip {3}{R}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{3}{R}{W}"), false));
    }

    private BladeholdWarWhip(final BladeholdWarWhip card) {
        super(card);
    }

    @Override
    public BladeholdWarWhip copy() {
        return new BladeholdWarWhip(this);
    }
}
