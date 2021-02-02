
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ArmoredGriffin extends CardImpl {

    public ArmoredGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private ArmoredGriffin(final ArmoredGriffin card) {
        super(card);
    }

    @Override
    public ArmoredGriffin copy() {
        return new ArmoredGriffin(this);
    }
}
