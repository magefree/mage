
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 *
 */
public final class PlagueSpores extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public PlagueSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{R}");

        // Destroy target nonblack creature and target land. They can't be regenerated.
        Effect effect = new DestroyTargetEffect(true, true);
        effect.setText("Destroy target nonblack creature and target land. They can't be regenerated.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public PlagueSpores(final PlagueSpores card) {
        super(card);
    }

    @Override
    public PlagueSpores copy() {
        return new PlagueSpores(this);
    }
}
