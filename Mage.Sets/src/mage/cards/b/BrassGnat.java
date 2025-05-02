
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
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author nigelzor
 */
public final class BrassGnat extends CardImpl {

    public BrassGnat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Brass Gnat doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));
        // At the beginning of your upkeep, you may pay {1}. If you do, untap Brass Gnat.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(new UntapSourceEffect(), new GenericManaCost(1))
                        .withChooseHint(new ConditionHint(SourceTappedCondition.UNTAPPED))
        ));
    }

    private BrassGnat(final BrassGnat card) {
        super(card);
    }

    @Override
    public BrassGnat copy() {
        return new BrassGnat(this);
    }
}
