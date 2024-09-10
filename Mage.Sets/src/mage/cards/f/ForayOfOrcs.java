package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForayOfOrcs extends CardImpl {

    public ForayOfOrcs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Amass Orcs 2. When you do, Foray of Orcs deals X damage to target creature an opponent controls, where X is the amassed Army's power.
        this.getSpellAbility().addEffect(new ForayOfOrcsEffect());
    }

    private ForayOfOrcs(final ForayOfOrcs card) {
        super(card);
    }

    @Override
    public ForayOfOrcs copy() {
        return new ForayOfOrcs(this);
    }
}

class ForayOfOrcsEffect extends OneShotEffect {

    private static final class ForayOfOrcsValue implements DynamicValue {

        private final Permanent permanent;

        private ForayOfOrcsValue(Permanent permanent) {
            this.permanent = permanent;
        }

        private ForayOfOrcsValue(final ForayOfOrcsValue value) {
            this.permanent = value.permanent;
        }

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return permanent.getPower().getValue();
        }

        @Override
        public ForayOfOrcsValue copy() {
            return new ForayOfOrcsValue(this);
        }

        @Override
        public String getMessage() {
            return "the amassed Army's power";
        }

        @Override
        public String toString() {
            return "X";
        }
    }

    ForayOfOrcsEffect() {
        super(Outcome.Benefit);
        staticText = "amass Orcs 2. When you do, {this} deals X damage to " +
                "target creature an opponent controls, where X is the amassed Army's power";
    }

    private ForayOfOrcsEffect(final ForayOfOrcsEffect effect) {
        super(effect);
    }

    @Override
    public ForayOfOrcsEffect copy() {
        return new ForayOfOrcsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = AmassEffect.doAmass(2, SubType.ORC, game, source);
        if (permanent == null) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(new ForayOfOrcsValue(permanent)), false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
