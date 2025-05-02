

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class NecrogenScudder extends CardImpl {

    public NecrogenScudder (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(3)));
        this.addAbility(FlyingAbility.getInstance());
    }

    private NecrogenScudder(final NecrogenScudder card) {
        super(card);
    }

    @Override
    public NecrogenScudder copy() {
        return new NecrogenScudder(this);
    }

}
