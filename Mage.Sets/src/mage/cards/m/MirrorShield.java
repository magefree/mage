package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBlockedByCreatureSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorShield extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    private static final String triggerPhrase
            = "Whenever a creature with deathtouch blocks or becomes blocked by this creature, ";

    public MirrorShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+2 and has hexproof and "Whenever a creature with deathtouch blocks or becomes blocked by this creature, destroy that creature."
        Ability gainedAbility = new BlocksOrBlockedByCreatureSourceTriggeredAbility(new DestroyTargetEffect(), filter).setTriggerPhrase(triggerPhrase);
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has hexproof"));
        ability.addEffect(new GainAbilityAttachedEffect(
                gainedAbility, AttachmentType.EQUIPMENT
        ).setText("and \"" + triggerPhrase + "destroy that creature.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MirrorShield(final MirrorShield card) {
        super(card);
    }

    @Override
    public MirrorShield copy() {
        return new MirrorShield(this);
    }
}
