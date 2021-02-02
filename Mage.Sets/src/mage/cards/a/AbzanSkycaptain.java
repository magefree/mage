
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AbzanSkycaptain extends CardImpl {

    public AbzanSkycaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Abzan Captain dies, bolster 2.
        this.addAbility(new DiesSourceTriggeredAbility(new BolsterEffect(2)));
    }

    private AbzanSkycaptain(final AbzanSkycaptain card) {
        super(card);
    }

    @Override
    public AbzanSkycaptain copy() {
        return new AbzanSkycaptain(this);
    }
}
