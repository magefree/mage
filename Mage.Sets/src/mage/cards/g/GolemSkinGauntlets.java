
package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttachedToAttachedPredicate;

import java.util.UUID;

/**
 * @author North
 */
public final class GolemSkinGauntlets extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("Equipment attached to it");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter.add(AttachedToAttachedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public GolemSkinGauntlets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(xValue, StaticValue.get(0))));

        // Equip 2 (2: Attach to target creature you control. Equip only as a sorcery. This card enters the battlefield unattached and stays on the battlefield if the creature leaves.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private GolemSkinGauntlets(final GolemSkinGauntlets card) {
        super(card);
    }

    @Override
    public GolemSkinGauntlets copy() {
        return new GolemSkinGauntlets(this);
    }
}
