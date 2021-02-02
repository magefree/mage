
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DeathgazeCockatrice extends CardImpl {

    public DeathgazeCockatrice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.COCKATRICE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private DeathgazeCockatrice(final DeathgazeCockatrice card) {
        super(card);
    }

    @Override
    public DeathgazeCockatrice copy() {
        return new DeathgazeCockatrice(this);
    }
}
