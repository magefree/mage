package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ManifestDreadThenAttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DissectionTools extends CardImpl {

    public DissectionTools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Dissection Tools enters, manifest dread, then attach Dissection Tools to that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestDreadThenAttachEffect()));

        // Equipped creature gets +2/+2 and has deathtouch and lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has deathtouch"));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and lifelink"));
        this.addAbility(ability);

        // Equip--Sacrifice a creature.
        this.addAbility(new EquipAbility(
                Outcome.BoostCreature, new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_A_CREATURE), false
        ));
    }

    private DissectionTools(final DissectionTools card) {
        super(card);
    }

    @Override
    public DissectionTools copy() {
        return new DissectionTools(this);
    }
}
