
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Svyatoslav28
 */
public final class PhyrexianNegator extends CardImpl {

    public PhyrexianNegator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Phyrexian Negator is dealt damage, sacrifice that many permanents.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENTS, SavedDamageValue.MANY, ""), false));
    }

    private PhyrexianNegator(final PhyrexianNegator card) {
        super(card);
    }

    @Override
    public PhyrexianNegator copy() {
        return new PhyrexianNegator(this);
    }
}
