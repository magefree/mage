
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox
 */
public final class DrossHarvester extends CardImpl {

    public DrossHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));
        // At the beginning of your end step, you lose 4 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new LoseLifeSourceControllerEffect(4),
           TargetController.YOU, false));
        // Whenever a creature dies, you gain 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(2), false));
    }

    private DrossHarvester(final DrossHarvester card) {
        super(card);
    }

    @Override
    public DrossHarvester copy() {
        return new DrossHarvester(this);
    }
}
