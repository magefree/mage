package mage.cards.d;

import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.hint.common.ControlACommanderHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyRollick extends CardImpl {

    public DeadlyRollick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // If you control a commander, you may cast this card without paying its mana cost
        this.addAbility(new AlternativeCostSourceAbility(null, ControlACommanderCondition.instance)
                .addHint(ControlACommanderHint.instance)
        );

        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeadlyRollick(final DeadlyRollick card) {
        super(card);
    }

    @Override
    public DeadlyRollick copy() {
        return new DeadlyRollick(this);
    }
}
