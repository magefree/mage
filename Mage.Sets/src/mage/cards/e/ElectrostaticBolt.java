
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class ElectrostaticBolt extends CardImpl {

    public ElectrostaticBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Electrostatic Bolt deals 2 damage to target creature. If it's an artifact creature, Electrostatic Bolt deals 4 damage to it instead.
        Effect effect = new DamageTargetEffect(ElectrostaticBoltDamageValue.instance);
        effect.setText("{this} deals 2 damage to target creature. If it's an artifact creature, {this} deals 4 damage to it instead.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ElectrostaticBolt(final ElectrostaticBolt card) {
        super(card);
    }

    @Override
    public ElectrostaticBolt copy() {
        return new ElectrostaticBolt(this);
    }
}

enum ElectrostaticBoltDamageValue implements DynamicValue {
    instance;

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent targetPermanent = game.getPermanent(sourceAbility.getFirstTarget());
        if (targetPermanent != null) {
            if (filter.match(targetPermanent, game)) {
                return 4;
            }
            return 2;
        }
        return 0;
    }

    @Override
    public ElectrostaticBoltDamageValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

