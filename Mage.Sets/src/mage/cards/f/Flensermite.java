

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Flensermite extends CardImpl {

    public Flensermite (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GREMLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
    }

    private Flensermite(final Flensermite card) {
        super(card);
    }

    @Override
    public Flensermite copy() {
        return new Flensermite(this);
    }

}
