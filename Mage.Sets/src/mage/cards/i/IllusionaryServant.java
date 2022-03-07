
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
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
public final class IllusionaryServant extends CardImpl {

    public IllusionaryServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")));
    }

    private IllusionaryServant(final IllusionaryServant card) {
        super(card);
    }

    @Override
    public IllusionaryServant copy() {
        return new IllusionaryServant(this);
    }
}
