package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttachableToRestrictedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetCard;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GateSmasher extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }

    public GateSmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Gate Smasher can be attached only to a creature with toughness 4 or greater.
        this.addAbility(new AttachableToRestrictedAbility(new TargetPermanent(filter)));

        // Equipped creature gets +3/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private GateSmasher(final GateSmasher card) {
        super(card);
    }

    @Override
    public GateSmasher copy() {
        return new GateSmasher(this);
    }
}
