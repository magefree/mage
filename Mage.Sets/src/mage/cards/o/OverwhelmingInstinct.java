
package mage.cards.o;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Wehk
 */
public final class OverwhelmingInstinct extends CardImpl {

    public OverwhelmingInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you attack with three or more creatures, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 3));
    }

    private OverwhelmingInstinct(final OverwhelmingInstinct card) {
        super(card);
    }

    @Override
    public OverwhelmingInstinct copy() {
        return new OverwhelmingInstinct(this);
    }
}