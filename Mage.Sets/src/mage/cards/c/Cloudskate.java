
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FadingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author markedagain
 */
public final class Cloudskate extends CardImpl {

    public Cloudskate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Fading 3
        this.addAbility(new FadingAbility(3, this));
    }

    private Cloudskate(final Cloudskate card) {
        super(card);
    }

    @Override
    public Cloudskate copy() {
        return new Cloudskate(this);
    }
}
