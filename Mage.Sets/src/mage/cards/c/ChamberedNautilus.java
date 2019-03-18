
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ChamberedNautilus extends CardImpl {

    public ChamberedNautilus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.NAUTILUS);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Chambered Nautilus becomes blocked, you may draw a card.
        this.addAbility(new BecomesBlockedTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    public ChamberedNautilus(final ChamberedNautilus card) {
        super(card);
    }

    @Override
    public ChamberedNautilus copy() {
        return new ChamberedNautilus(this);
    }
}
