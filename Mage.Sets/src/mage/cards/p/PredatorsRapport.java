
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class PredatorsRapport extends CardImpl {

    public PredatorsRapport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Choose target creature you control. You gain life equal to that creature's power plus its toughness.
        Effect effect = new GainLifeEffect(new TargetPermanentPowerPlusToughnessCount());
        effect.setText("Choose target creature you control. You gain life equal to that creature's power plus its toughness");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private PredatorsRapport(final PredatorsRapport card) {
        super(card);
    }

    @Override
    public PredatorsRapport copy() {
        return new PredatorsRapport(this);
    }
}

class TargetPermanentPowerPlusToughnessCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getFirstTarget());
        if (sourcePermanent != null) {
            return CardUtil.overflowInc(sourcePermanent.getPower().getValue(), sourcePermanent.getToughness().getValue());
        }
        return 0;
    }

    @Override
    public TargetPermanentPowerPlusToughnessCount copy() {
        return new TargetPermanentPowerPlusToughnessCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "that creature's power plus its toughness";
    }
}
