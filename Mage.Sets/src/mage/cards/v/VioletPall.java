
package mage.cards.v;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.FaerieRogueToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VioletPall extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public VioletPall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{4}{B}");
        this.subtype.add(SubType.FAERIE);

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieRogueToken(), 1));
    }

    public VioletPall(final VioletPall card) {
        super(card);
    }

    @Override
    public VioletPall copy() {
        return new VioletPall(this);
    }
}
