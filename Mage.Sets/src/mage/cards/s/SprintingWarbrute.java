
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SprintingWarbrute extends CardImpl {

    public SprintingWarbrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Sprinting Warbrute attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // Dash {3}{R}
        this.addAbility(new DashAbility("{3}{R}"));
    }

    private SprintingWarbrute(final SprintingWarbrute card) {
        super(card);
    }

    @Override
    public SprintingWarbrute copy() {
        return new SprintingWarbrute(this);
    }
}
