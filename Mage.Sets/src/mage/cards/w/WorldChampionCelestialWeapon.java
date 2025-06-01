package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorldChampionCelestialWeapon extends CardImpl {

    public WorldChampionCelestialWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);
        this.nightCard = true;
        this.color.setRed(true);

        // Double Overdrive -- Equipped creature gets +2/+0 and has double strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has double strike"));
        this.addAbility(ability.withFlavorWord("Double Overdrive"));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private WorldChampionCelestialWeapon(final WorldChampionCelestialWeapon card) {
        super(card);
    }

    @Override
    public WorldChampionCelestialWeapon copy() {
        return new WorldChampionCelestialWeapon(this);
    }
}
