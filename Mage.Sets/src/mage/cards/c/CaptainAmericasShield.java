package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsAttachedAttackingPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CaptainAmericasShield extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsAttachedAttackingPredicate.instance);
    }

    public CaptainAmericasShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Equipped creature gets +0/+8 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 8));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has vigilance"));
        this.addAbility(ability);

        // Whenever equipped creature attacks, tap target creature defending player controls.
        Ability ability2 = new AttacksAttachedTriggeredAbility(new TapTargetEffect());
        ability2.addTarget(new TargetPermanent(filter));
        this.addAbility(ability2);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private CaptainAmericasShield(final CaptainAmericasShield card) {
        super(card);
    }

    @Override
    public CaptainAmericasShield copy() {
        return new CaptainAmericasShield(this);
    }
}
