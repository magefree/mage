
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class SteamBlast extends CardImpl {

    public SteamBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Steam Blast deals 2 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2));
    }

    private SteamBlast(final SteamBlast card) {
        super(card);
    }

    @Override
    public SteamBlast copy() {
        return new SteamBlast(this);
    }
}
