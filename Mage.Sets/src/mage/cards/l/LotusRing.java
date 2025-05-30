package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LotusRing extends CardImpl {

    public LotusRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +3/+3 and has vigilance and "{T}, Sacrifice this creature: Add three mana of any one color."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        Ability manaAbility = new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()
        );
        manaAbility.addCost(new SacrificeSourceCost().setText("sacrifice this creature"));
        ability.addEffect(new GainAbilityAttachedEffect(
                manaAbility, AttachmentType.EQUIPMENT
        ).setText("and \"{T}, Sacrifice this creature: Add three mana of any one color.\""));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private LotusRing(final LotusRing card) {
        super(card);
    }

    @Override
    public LotusRing copy() {
        return new LotusRing(this);
    }
}
