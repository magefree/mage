
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class MetallicRebuke extends CardImpl {

    public MetallicRebuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        this.addAbility(new ImproviseAbility());

        // Counter target spell unless its controller pays {3}.
        getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        getSpellAbility().addTarget(new TargetSpell());
    }

    private MetallicRebuke(final MetallicRebuke card) {
        super(card);
    }

    @Override
    public MetallicRebuke copy() {
        return new MetallicRebuke(this);
    }
}
