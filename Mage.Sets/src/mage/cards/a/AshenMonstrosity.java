
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class AshenMonstrosity extends CardImpl {

    public AshenMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);
        this.addAbility(HasteAbility.getInstance());
        // Ashen Monstrosity attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private AshenMonstrosity(final AshenMonstrosity card) {
        super(card);
    }

    @Override
    public AshenMonstrosity copy() {
        return new AshenMonstrosity(this);
    }
}
