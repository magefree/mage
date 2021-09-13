
package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class AvacynianMissionaries extends CardImpl {

    public AvacynianMissionaries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.l.LunarchInquisitors.class;

        // At the beginning of your end step, if Avacynian Missionaries is equipped, transform it.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), TargetController.YOU, EquippedSourceCondition.instance, false));

    }

    private AvacynianMissionaries(final AvacynianMissionaries card) {
        super(card);
    }

    @Override
    public AvacynianMissionaries copy() {
        return new AvacynianMissionaries(this);
    }
}
