
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OblivionStrike extends CardImpl {

    public OblivionStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Exile target creature.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OblivionStrike(final OblivionStrike card) {
        super(card);
    }

    @Override
    public OblivionStrike copy() {
        return new OblivionStrike(this);
    }
}
