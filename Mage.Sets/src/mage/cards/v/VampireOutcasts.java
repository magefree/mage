
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class VampireOutcasts extends CardImpl {

    public VampireOutcasts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new BloodthirstAbility(2));
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VampireOutcasts(final VampireOutcasts card) {
        super(card);
    }

    @Override
    public VampireOutcasts copy() {
        return new VampireOutcasts(this);
    }
}
