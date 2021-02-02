
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class ScorchTheFields extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Human creature");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public ScorchTheFields(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");


        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        // Scorch the Fields deals 1 damage to each Human creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));
    }

    private ScorchTheFields(final ScorchTheFields card) {
        super(card);
    }

    @Override
    public ScorchTheFields copy() {
        return new ScorchTheFields(this);
    }
}
