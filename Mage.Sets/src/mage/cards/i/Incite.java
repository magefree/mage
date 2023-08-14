

package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Incite extends CardImpl {

    public Incite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Target creature becomes red until end of turn and attacks this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.RED, Duration.EndOfTurn, "Target creature becomes red until end of turn"));
        this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn).setText("and attacks this turn if able"));
    }

    private Incite(final Incite card) {
        super(card);
    }

    @Override
    public Incite copy() {
        return new Incite(this);
    }
}
