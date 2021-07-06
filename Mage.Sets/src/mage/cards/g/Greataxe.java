package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Greataxe extends CardImpl {

    public Greataxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 0)));

        // Equip {5}
        this.addAbility(new EquipAbility(5));
    }

    private Greataxe(final Greataxe card) {
        super(card);
    }

    @Override
    public Greataxe copy() {
        return new Greataxe(this);
    }
}
