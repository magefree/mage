package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HedgewitchsMask extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public HedgewitchsMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equipped creature can't be blocked by creatures with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAttachedEffect(
                Duration.WhileOnBattlefield, filter, AttachmentType.EQUIPMENT
        )));
        
        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HedgewitchsMask(final HedgewitchsMask card) {
        super(card);
    }

    @Override
    public HedgewitchsMask copy() {
        return new HedgewitchsMask(this);
    }
}
