
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class UrborgDrake extends CardImpl {

    public UrborgDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Urborg Drake attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private UrborgDrake(final UrborgDrake card) {
        super(card);
    }

    @Override
    public UrborgDrake copy() {
        return new UrborgDrake(this);
    }
}
