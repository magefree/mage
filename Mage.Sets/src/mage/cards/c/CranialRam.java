package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CranialRam extends CardImpl {

    public CranialRam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +X/+1, where X is the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostEquippedEffect(ArtifactYouControlCount.instance, StaticValue.get(1))
                        .setText("equipped creature gets +X/+1, where X is the number of artifacts you control")
        ).addHint(ArtifactYouControlHint.instance));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private CranialRam(final CranialRam card) {
        super(card);
    }

    @Override
    public CranialRam copy() {
        return new CranialRam(this);
    }
}
