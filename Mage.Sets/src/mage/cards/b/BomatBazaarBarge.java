
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class BomatBazaarBarge extends CardImpl {

    public BomatBazaarBarge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Bomat Bazaar Barge enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private BomatBazaarBarge(final BomatBazaarBarge card) {
        super(card);
    }

    @Override
    public BomatBazaarBarge copy() {
        return new BomatBazaarBarge(this);
    }
}
