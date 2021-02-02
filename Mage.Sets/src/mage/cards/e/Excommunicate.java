

package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Excommunicate extends CardImpl {

    public Excommunicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
    }

    private Excommunicate(final Excommunicate card) {
        super(card);
    }

    @Override
    public Excommunicate copy() {
        return new Excommunicate(this);
    }
}
