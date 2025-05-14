package mage.cards.j;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudgmentBolt extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.EQUIPMENT));
    private static final Hint hint = new ValueHint("Equipment you control", xValue);

    public JudgmentBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Judgment Bolt deals 5 damage to target creature and X damage to that creature's controller, where X is the number of Equipment you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(xValue)
                .setText("and X damage to that creature's controller, where X is the number of Equipment you control"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private JudgmentBolt(final JudgmentBolt card) {
        super(card);
    }

    @Override
    public JudgmentBolt copy() {
        return new JudgmentBolt(this);
    }
}
