package mage.cards.b;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetPerpetuallyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.FrogToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author karapuzz14
 */
public final class BafflingDefenses extends CardImpl {

    public BafflingDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        

        // Target creature's base power perpetually becomes 0.
        Effect effect = new SetBasePowerToughnessTargetPerpetuallyEffect(StaticValue.get(0), null);
        effect.setText("Target creature’s base power perpetually becomes 0");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private BafflingDefenses(final BafflingDefenses card) {
        super(card);
    }

    @Override
    public BafflingDefenses copy() {
        return new BafflingDefenses(this);
    }
}
