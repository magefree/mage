package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldwardensHelm extends CardImpl {

    public GoldwardensHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +0/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(0, 1)));

        // Equip {1}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{1}{W}")));
    }

    private GoldwardensHelm(final GoldwardensHelm card) {
        super(card);
    }

    @Override
    public GoldwardensHelm copy() {
        return new GoldwardensHelm(this);
    }
}
