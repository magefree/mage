
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class CoastalDiscovery extends CardImpl {

    public CoastalDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Awaken 4 - {5}{U}
        this.addAbility(new AwakenAbility(this, 4, "{5}{U}"));
    }

    private CoastalDiscovery(final CoastalDiscovery card) {
        super(card);
    }

    @Override
    public CoastalDiscovery copy() {
        return new CoastalDiscovery(this);
    }
}
