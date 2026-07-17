package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetAndTargetControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudgmentBolt extends CardImpl {

    static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.EQUIPMENT));
    private static final Hint hint = new ValueHint("Equipment you control", xValue);

    public JudgmentBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Judgment Bolt deals 5 damage to target creature and X damage to that creature's controller, where X is the number of Equipment you control.
        this.getSpellAbility().addEffect(new JudgmentBoltEffect());
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

// too lazy to handle dynamic value properly in the common class
class JudgmentBoltEffect extends OneShotEffect {

    JudgmentBoltEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to target creature and X damage to that creature's controller," +
                " where X is the number of Equipment you control";
    }

    private JudgmentBoltEffect(final JudgmentBoltEffect effect) {
        super(effect);
    }

    @Override
    public JudgmentBoltEffect copy() {
        return new JudgmentBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new DamageTargetAndTargetControllerEffect(5, JudgmentBolt.xValue.calculate(game, source, this))
                .apply(game, source);
    }
}
