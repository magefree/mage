
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class VengefulVampire extends CardImpl {

    public VengefulVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private VengefulVampire(final VengefulVampire card) {
        super(card);
    }

    @Override
    public VengefulVampire copy() {
        return new VengefulVampire(this);
    }
}
