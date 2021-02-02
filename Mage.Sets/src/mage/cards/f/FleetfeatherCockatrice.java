
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class FleetfeatherCockatrice extends CardImpl {

    public FleetfeatherCockatrice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{U}");
        this.subtype.add(SubType.COCKATRICE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // {5}{G}{U}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{5}{G}{U}",3));
    }

    private FleetfeatherCockatrice(final FleetfeatherCockatrice card) {
        super(card);
    }

    @Override
    public FleetfeatherCockatrice copy() {
        return new FleetfeatherCockatrice(this);
    }
}
