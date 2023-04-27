
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class Skizzik extends CardImpl {

    public Skizzik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of the end step, sacrifice Skizzik unless it was kicked.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceUnlessConditionEffect(KickedCondition.ONCE), TargetController.NEXT, false));
    }

    private Skizzik(final Skizzik card) {
        super(card);
    }

    @Override
    public Skizzik copy() {
        return new Skizzik(this);
    }
}
