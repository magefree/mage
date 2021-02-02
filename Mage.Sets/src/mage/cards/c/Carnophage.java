
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class Carnophage extends CardImpl {

    public Carnophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, tap Carnophage unless you pay 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(1)), TargetController.YOU, false));
    }

    private Carnophage(final Carnophage card) {
        super(card);
    }

    @Override
    public Carnophage copy() {
        return new Carnophage(this);
    }
}
