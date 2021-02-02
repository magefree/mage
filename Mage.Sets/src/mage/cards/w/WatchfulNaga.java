
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class WatchfulNaga extends CardImpl {

    public WatchfulNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may exert Watchful Naga as it attacks. When you do, draw a card.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new DrawCardSourceControllerEffect(1));
        this.addAbility(new ExertAbility(ability));
    }

    private WatchfulNaga(final WatchfulNaga card) {
        super(card);
    }

    @Override
    public WatchfulNaga copy() {
        return new WatchfulNaga(this);
    }
}
