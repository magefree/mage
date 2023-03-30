package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class KembasBanner extends CardImpl {

    public KembasBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");
        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Equipped creature gets +1/+1 for each creature you control.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(value, value)));

        // Equip {2}{W}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{W}"), false));
    }

    private KembasBanner(final KembasBanner card) {
        super(card);
    }

    @Override
    public KembasBanner copy() {
        return new KembasBanner(this);
    }
}
