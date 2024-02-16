
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ShadowAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class InfiltratorIlKor extends CardImpl {

    public InfiltratorIlKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Suspend 2-{1}{U}
        this.addAbility(new SuspendAbility(2, new ManaCostsImpl<>("{1}{U}"), this));
    }

    private InfiltratorIlKor(final InfiltratorIlKor card) {
        super(card);
    }

    @Override
    public InfiltratorIlKor copy() {
        return new InfiltratorIlKor(this);
    }
}
