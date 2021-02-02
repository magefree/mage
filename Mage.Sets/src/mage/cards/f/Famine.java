
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class Famine extends CardImpl {

    public Famine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Famine deals 3 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(3));
    }

    private Famine(final Famine card) {
        super(card);
    }

    @Override
    public Famine copy() {
        return new Famine(this);
    }
}
