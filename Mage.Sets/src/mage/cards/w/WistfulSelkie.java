

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WistfulSelkie extends CardImpl {

    public WistfulSelkie (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/U}{G/U}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private WistfulSelkie(final WistfulSelkie card) {
        super(card);
    }

    @Override
    public WistfulSelkie copy() {
        return new WistfulSelkie(this);
    }

}
