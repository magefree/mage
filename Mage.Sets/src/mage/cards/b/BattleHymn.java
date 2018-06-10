
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class BattleHymn extends CardImpl {

    public BattleHymn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Add {R} for each creature you control.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)));
    }

    public BattleHymn(final BattleHymn card) {
        super(card);
    }

    @Override
    public BattleHymn copy() {
        return new BattleHymn(this);
    }
}
