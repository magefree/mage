
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PhantasmalDragon extends CardImpl {

    public PhantasmalDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
    }

    private PhantasmalDragon(final PhantasmalDragon card) {
        super(card);
    }

    @Override
    public PhantasmalDragon copy() {
        return new PhantasmalDragon(this);
    }
}
