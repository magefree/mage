
package mage.cards.g;

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
public final class GiantDustwasp extends CardImpl {

    public GiantDustwasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Suspend 4-{1}{G}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{1}{G}"), this));
    }

    private GiantDustwasp(final GiantDustwasp card) {
        super(card);
    }

    @Override
    public GiantDustwasp copy() {
        return new GiantDustwasp(this);
    }
}
