package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Galatolol
 */
public final class Heartseeker extends CardImpl {

    public Heartseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1 and has "{tap}, Unattach Heartseeker: Destroy target creature."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityWithAttachmentEffect(
                "and has \"{T}, Unattach {this}: Destroy target creature.\"", new DestroyTargetEffect(),
                new TargetCreaturePermanent(), new UnattachCost(), new TapSourceCost()
        ));
        this.addAbility(ability);

        // Equip {5}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(5)));
    }

    private Heartseeker(final Heartseeker card) {
        super(card);
    }

    @Override
    public Heartseeker copy() {
        return new Heartseeker(this);
    }
}
