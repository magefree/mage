
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ImpetuousSunchaser extends CardImpl {

    public ImpetuousSunchaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Impetuous Sunchaser attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private ImpetuousSunchaser(final ImpetuousSunchaser card) {
        super(card);
    }

    @Override
    public ImpetuousSunchaser copy() {
        return new ImpetuousSunchaser(this);
    }
}
