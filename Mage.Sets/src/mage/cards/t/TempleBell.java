

package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TempleBell extends CardImpl {

    public TempleBell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardAllEffect(1), new TapSourceCost()));
    }

    private TempleBell(final TempleBell card) {
        super(card);
    }

    @Override
    public TempleBell copy() {
        return new TempleBell(this);
    }

}
