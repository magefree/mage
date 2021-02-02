
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class BarricadeBreaker extends CardImpl {

    public BarricadeBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        this.addAbility(new ImproviseAbility());
        
        // Barricade Breaker attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private BarricadeBreaker(final BarricadeBreaker card) {
        super(card);
    }

    @Override
    public BarricadeBreaker copy() {
        return new BarricadeBreaker(this);
    }
}
