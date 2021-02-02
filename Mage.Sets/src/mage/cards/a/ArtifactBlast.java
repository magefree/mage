
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactSpell;
import mage.target.TargetSpell;

/**
 *
 * @author Jgod
 */
public final class ArtifactBlast extends CardImpl {
    public ArtifactBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Counter target artifact spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterArtifactSpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private ArtifactBlast(final ArtifactBlast card) {
        super(card);
    }

    @Override
    public ArtifactBlast copy() {
        return new ArtifactBlast(this);
    }
}
