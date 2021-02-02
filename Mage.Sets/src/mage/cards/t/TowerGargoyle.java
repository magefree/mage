
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TowerGargoyle extends CardImpl {

    public TowerGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{W}{U}{B}");
        this.subtype.add(SubType.GARGOYLE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
    }

    private TowerGargoyle(final TowerGargoyle card) {
        super(card);
    }

    @Override
    public TowerGargoyle copy() {
        return new TowerGargoyle(this);
    }
}
