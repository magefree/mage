
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class MightOfTheNephilim extends CardImpl {

    public MightOfTheNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target creature gets +2/+2 until end of turn for each of its colors.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                MightOfTheNephilimValue.instance, MightOfTheNephilimValue.instance, Duration.EndOfTurn
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MightOfTheNephilim(final MightOfTheNephilim card) {
        super(card);
    }

    @Override
    public MightOfTheNephilim copy() {
        return new MightOfTheNephilim(this);
    }
}

enum MightOfTheNephilimValue implements DynamicValue {

   instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent target = game.getPermanentOrLKIBattlefield(sourceAbility.getFirstTarget());
        if (target != null) {
            return 2 * target.getColor(game).getColorCount();
        }
        return 0;
    }

    @Override
    public MightOfTheNephilimValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public String getMessage() {
        return "of its colors";
    }
}
