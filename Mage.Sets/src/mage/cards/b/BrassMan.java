
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author KholdFuzion
 */
public final class BrassMan extends CardImpl {

    public BrassMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Brass Man doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));
        // At the beginning of your upkeep, you may pay {1}. If you do, untap Brass Man.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(new UntapSourceEffect(), new GenericManaCost(1))
                        .withChooseHint(new ConditionHint(SourceTappedCondition.UNTAPPED))
        ));
    }

    private BrassMan(final BrassMan card) {
        super(card);
    }

    @Override
    public BrassMan copy() {
        return new BrassMan(this);
    }
}
