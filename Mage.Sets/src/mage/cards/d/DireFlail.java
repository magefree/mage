package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DireFlail extends CardImpl {

    public DireFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");
        this.secondSideCardClazz = mage.cards.d.DireBlunderbuss.class;

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));

        // Craft with artifact {3}{R}{R}
        this.addAbility(new CraftAbility("{3}{R}{R}"));
    }

    private DireFlail(final DireFlail card) {
        super(card);
    }

    @Override
    public DireFlail copy() {
        return new DireFlail(this);
    }
}
