
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class ThievingMagpie extends CardImpl {

    public ThievingMagpie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Thieving Magpie deals damage to an opponent, draw a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ThievingMagpie(final ThievingMagpie card) {
        super(card);
    }

    @Override
    public ThievingMagpie copy() {
        return new ThievingMagpie(this);
    }
}
