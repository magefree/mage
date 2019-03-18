
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class GalvanicBlast extends CardImpl {

    private static final String effectText = "{this} deals 2 damage to anytarget.<br><i>Metalcraft</i> &mdash; {this} deals 4 damage to that permanent or player instead if you control three or more artifacts";

    public GalvanicBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");
        this.color.setRed(true);

        // Galvanic Blast deals 2 damage to any target.
        // <i>Metalcraft</i> &mdash; Galvanic Blast deals 4 damage to that creature or player instead if you control three or more artifacts.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DamageTargetEffect(4), new DamageTargetEffect(2), MetalcraftCondition.instance, effectText));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public GalvanicBlast(final GalvanicBlast card) {
        super(card);
    }

    @Override
    public GalvanicBlast copy() {
        return new GalvanicBlast(this);
    }
}
