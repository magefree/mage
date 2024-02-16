
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Gigadrowse extends CardImpl {

    public Gigadrowse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Replicate {U}
        this.addAbility(new ReplicateAbility("{U}"));
        // Tap target permanent.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Gigadrowse(final Gigadrowse card) {
        super(card);
    }

    @Override
    public Gigadrowse copy() {
        return new Gigadrowse(this);
    }
}
