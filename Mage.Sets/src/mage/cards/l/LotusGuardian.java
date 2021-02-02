
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class LotusGuardian extends CardImpl {

    public LotusGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private LotusGuardian(final LotusGuardian card) {
        super(card);
    }

    @Override
    public LotusGuardian copy() {
        return new LotusGuardian(this);
    }
}
