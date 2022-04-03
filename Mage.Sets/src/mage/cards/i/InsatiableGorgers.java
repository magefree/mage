
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class InsatiableGorgers extends CardImpl {

    public InsatiableGorgers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Insatiable Gorgers attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Madness {3}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{3}{R}")));
    }

    private InsatiableGorgers(final InsatiableGorgers card) {
        super(card);
    }

    @Override
    public InsatiableGorgers copy() {
        return new InsatiableGorgers(this);
    }
}
