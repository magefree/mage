
package mage.cards.u;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class UrzasBlueprints extends CardImpl {

    public UrzasBlueprints(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Echo {6}
        this.addAbility(new EchoAbility("{6}"));
        // {tap}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new TapSourceCost()));
    }

    private UrzasBlueprints(final UrzasBlueprints card) {
        super(card);
    }

    @Override
    public UrzasBlueprints copy() {
        return new UrzasBlueprints(this);
    }
}
