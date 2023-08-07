
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;

/**
 *
 * @author North
 */
public final class SlagFiend extends CardImpl {

    public SlagFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(
                new CardsInAllGraveyardsCount(new FilterArtifactCard("artifact cards")))));
    }

    private SlagFiend(final SlagFiend card) {
        super(card);
    }

    @Override
    public SlagFiend copy() {
        return new SlagFiend(this);
    }
}
