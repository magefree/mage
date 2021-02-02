
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Jgod
 */
public final class GoblinBrigand extends CardImpl {

    public GoblinBrigand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblin Brigand attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private GoblinBrigand(final GoblinBrigand card) {
        super(card);
    }

    @Override
    public GoblinBrigand copy() {
        return new GoblinBrigand(this);
    }
}
