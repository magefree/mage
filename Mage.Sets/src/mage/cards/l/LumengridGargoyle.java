

package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LumengridGargoyle extends CardImpl {

    public LumengridGargoyle (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
    }

    private LumengridGargoyle(final LumengridGargoyle card) {
        super(card);
    }

    @Override
    public LumengridGargoyle copy() {
        return new LumengridGargoyle(this);
    }

}
