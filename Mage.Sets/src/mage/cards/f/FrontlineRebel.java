
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class FrontlineRebel extends CardImpl {

    public FrontlineRebel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Frontline Rebel attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private FrontlineRebel(final FrontlineRebel card) {
        super(card);
    }

    @Override
    public FrontlineRebel copy() {
        return new FrontlineRebel(this);
    }
}
