
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class OjutaiInterceptor extends CardImpl {

    public OjutaiInterceptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Megamorph {3}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{3}{U}"), true));

    }

    private OjutaiInterceptor(final OjutaiInterceptor card) {
        super(card);
    }

    @Override
    public OjutaiInterceptor copy() {
        return new OjutaiInterceptor(this);
    }
}
