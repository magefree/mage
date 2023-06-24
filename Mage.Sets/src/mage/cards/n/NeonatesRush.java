package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeonatesRush extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterPermanent(SubType.VAMPIRE, "you control a Vampire")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Vampire");

    public NeonatesRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell costs {1} less to cast if you control a Vampire.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition)
        ).addHint(hint).setRuleAtTheTop(true));

        // Neonate's Rush deals 1 damage to target creature and 1 damage to its controller. Draw a card.
        this.getSpellAbility().addEffect(new NeonatesRushEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private NeonatesRush(final NeonatesRush card) {
        super(card);
    }

    @Override
    public NeonatesRush copy() {
        return new NeonatesRush(this);
    }
}

class NeonatesRushEffect extends OneShotEffect {

    NeonatesRushEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to target creature and 1 damage to its controller.";
    }

    private NeonatesRushEffect(final NeonatesRushEffect effect) {
        super(effect);
    }

    @Override
    public NeonatesRushEffect copy() {
        return new NeonatesRushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(1, source.getSourceId(), source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(1, source.getSourceId(), source, game);
        }
        return true;
    }
}
