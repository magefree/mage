
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class BlurredMongoose extends CardImpl {

    public BlurredMongoose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.MONGOOSE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Blurred Mongoose can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        this.addAbility(ShroudAbility.getInstance());
    }

    private BlurredMongoose(final BlurredMongoose card) {
        super(card);
    }

    @Override
    public BlurredMongoose copy() {
        return new BlurredMongoose(this);
    }
}
