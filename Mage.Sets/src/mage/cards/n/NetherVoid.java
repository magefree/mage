
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class NetherVoid extends CardImpl {

    public NetherVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.supertype.add(SuperType.WORLD);

        // Whenever a player casts a spell, counter it unless that player pays {3}.
        this.addAbility(new SpellCastAllTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(3)), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));
    }

    private NetherVoid(final NetherVoid card) {
        super(card);
    }

    @Override
    public NetherVoid copy() {
        return new NetherVoid(this);
    }
}
