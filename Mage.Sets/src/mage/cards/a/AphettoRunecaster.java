
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class AphettoRunecaster extends CardImpl {

    public AphettoRunecaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever a permanent is turned face up, you may draw a card.
        this.addAbility(new TurnedFaceUpAllTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new FilterPermanent("a permanent"), false, true));
    }

    private AphettoRunecaster(final AphettoRunecaster card) {
        super(card);
    }

    @Override
    public AphettoRunecaster copy() {
        return new AphettoRunecaster(this);
    }
}