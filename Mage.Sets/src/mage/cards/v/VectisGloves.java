package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class VectisGloves extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("artifact land");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public VectisGloves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has artifact landwalk.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new LandwalkAbility(filter), AttachmentType.EQUIPMENT
        ).setText("and has artifact landwalk"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private VectisGloves(final VectisGloves card) {
        super(card);
    }

    @Override
    public VectisGloves copy() {
        return new VectisGloves(this);
    }
}
