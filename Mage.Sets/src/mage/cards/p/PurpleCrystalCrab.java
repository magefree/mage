package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class PurpleCrystalCrab extends CardImpl {

    public PurpleCrystalCrab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Purple-Crystal Crab dies, draw card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private PurpleCrystalCrab(final PurpleCrystalCrab card) {
        super(card);
    }

    @Override
    public PurpleCrystalCrab copy() {
        return new PurpleCrystalCrab(this);
    }
}
