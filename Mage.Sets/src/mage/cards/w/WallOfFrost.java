
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class WallOfFrost extends CardImpl {

    public WallOfFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Frost blocks a creature, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new BlocksSourceTriggeredAbility(new DontUntapInControllersNextUntapStepTargetEffect("that creature"), false, true));
    }

    private WallOfFrost(final WallOfFrost card) {
        super(card);
    }

    @Override
    public WallOfFrost copy() {
        return new WallOfFrost(this);
    }
}
