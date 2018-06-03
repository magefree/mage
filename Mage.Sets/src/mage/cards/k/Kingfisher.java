
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class Kingfisher extends CardImpl {

    public Kingfisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Kingfisher dies, draw a card.
        this.addAbility(new DiesTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    public Kingfisher(final Kingfisher card) {
        super(card);
    }

    @Override
    public Kingfisher copy() {
        return new Kingfisher(this);
    }
}
