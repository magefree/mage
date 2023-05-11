
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DealsCombatDamageTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class DrinkerOfSorrow extends CardImpl {

    public DrinkerOfSorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Drinker of Sorrow can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever Drinker of Sorrow deals combat damage, sacrifice a permanent.
        this.addAbility(new DealsCombatDamageTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_A, 1, ""), false));
    }

    private DrinkerOfSorrow(final DrinkerOfSorrow card) {
        super(card);
    }

    @Override
    public DrinkerOfSorrow copy() {
        return new DrinkerOfSorrow(this);
    }
}
