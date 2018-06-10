
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class CrucibleOfWorlds extends CardImpl {

    public CrucibleOfWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // You may play land cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayLandsFromGraveyardEffect()));
    }

    public CrucibleOfWorlds(final CrucibleOfWorlds card) {
        super(card);
    }

    @Override
    public CrucibleOfWorlds copy() {
        return new CrucibleOfWorlds(this);
    }
}
