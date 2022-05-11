
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class PitilessHorde extends CardImpl {

    public PitilessHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, lose 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(2), TargetController.YOU, false));
        
        // Dash {2}{B}{B}
        this.addAbility(new DashAbility("{2}{B}{B}"));
    }

    private PitilessHorde(final PitilessHorde card) {
        super(card);
    }

    @Override
    public PitilessHorde copy() {
        return new PitilessHorde(this);
    }
}
