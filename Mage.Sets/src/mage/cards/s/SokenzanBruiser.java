

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SokenzanBruiser extends CardImpl {

    public SokenzanBruiser (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new MountainwalkAbility());
    }

    private SokenzanBruiser(final SokenzanBruiser card) {
        super(card);
    }

    @Override
    public SokenzanBruiser copy() {
        return new SokenzanBruiser(this);
    }

}
