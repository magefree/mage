package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class Bonehoard extends CardImpl {

    private static final DynamicValue value = new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE);

    public Bonehoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon (When this Equipment enters the battlefield, create a 0/0 black Germ creature token, then attach this to it.)
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +X/+X, where X is the number of creature cards in all graveyards.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(value, value).setText("equipped creature gets +X/+X, where X is the number of creature cards in all graveyards")));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private Bonehoard(final Bonehoard card) {
        super(card);
    }

    @Override
    public Bonehoard copy() {
        return new Bonehoard(this);
    }
}
