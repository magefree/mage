

package mage.cards.v;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class VaultOfWhispers extends CardImpl {

    public VaultOfWhispers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, null);
        this.addAbility(new BlackManaAbility());
    }

    private VaultOfWhispers(final VaultOfWhispers card) {
        super(card);
    }

    @Override
    public VaultOfWhispers copy() {
        return new VaultOfWhispers(this);
    }

}
