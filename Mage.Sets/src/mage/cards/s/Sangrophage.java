
package mage.cards.s;

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
public final class Sangrophage extends CardImpl {

    public Sangrophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, tap Sangrophage unless you pay 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), TargetController.YOU, false));

    }

    private Sangrophage(final Sangrophage card) {
        super(card);
    }

    @Override
    public Sangrophage copy() {
        return new Sangrophage(this);
    }
}
