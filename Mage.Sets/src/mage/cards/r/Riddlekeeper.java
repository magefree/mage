
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class Riddlekeeper extends CardImpl {

    public Riddlekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever a creature attacks you or a planeswalker you control, that creature's controller puts the top two cards of their library into their graveyard.
        Effect effect = new MillCardsTargetEffect(2);
        effect.setText("that creature's controller mills two cards");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER, true, true));
    }

    private Riddlekeeper(final Riddlekeeper card) {
        super(card);
    }

    @Override
    public Riddlekeeper copy() {
        return new Riddlekeeper(this);
    }
}
