
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ErrantEphemeron extends CardImpl {

    public ErrantEphemeron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Suspend 4-{1}{U}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{1}{U}"), this));
    }

    private ErrantEphemeron(final ErrantEphemeron card) {
        super(card);
    }

    @Override
    public ErrantEphemeron copy() {
        return new ErrantEphemeron(this);
    }
}
