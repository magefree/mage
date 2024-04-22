package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DeconstructionHammer extends CardImpl {

    public DeconstructionHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has "{3}, {T}, Sacrifice Deconstruction Hammer: Destroy target artifact or enchantment."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityWithAttachmentEffect(
                "and has \"{3}, {T}, Sacrifice {this}: Destroy target artifact or enchantment.\"",
                new DestroyTargetEffect(), new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT),
                new SacrificeAttachmentCost(), new GenericManaCost(3), new TapSourceCost()
        ));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private DeconstructionHammer(final DeconstructionHammer card) {
        super(card);
    }

    @Override
    public DeconstructionHammer copy() {
        return new DeconstructionHammer(this);
    }
}
