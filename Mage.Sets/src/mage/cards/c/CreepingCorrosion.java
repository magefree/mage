
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author Viserion
 */
public final class CreepingCorrosion extends CardImpl {

    public CreepingCorrosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Destroy all artifacts.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterArtifactPermanent("artifacts")));
    }

    private CreepingCorrosion(final CreepingCorrosion card) {
        super(card);
    }

    @Override
    public CreepingCorrosion copy() {
        return new CreepingCorrosion(this);
    }

}
