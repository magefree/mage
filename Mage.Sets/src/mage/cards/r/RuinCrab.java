package mage.cards.r;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 * @author TheElk801
 */
public final class RuinCrab extends CardImpl {

    public RuinCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Landfall - Whenever a land enters the battlefield under your control, each opponent mills 3 cards.
        this.addAbility(new LandfallAbility(new MillCardsEachPlayerEffect(3, TargetController.OPPONENT), false));
    }

    private RuinCrab(final RuinCrab card) {
        super(card);
    }

    @Override
    public RuinCrab copy() {
        return new RuinCrab(this);
    }
}
