
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class NissasJudgment extends CardImpl {

    public NissasJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Support 2.
        Effect effect = new SupportEffect(this, 2, false);
        getSpellAbility().addEffect(effect);

        // Choose up to one target creature an opponent controls. Each creature you control with a +1/+1 counter on it deals damage equal to its power to that creature.
        effect = new NissasJudgmentEffect();
        effect.setTargetPointer(new SecondTargetPointer()); // First target is used by Support
        getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE, false));
        getSpellAbility().addEffect(effect);
    }

    private NissasJudgment(final NissasJudgment card) {
        super(card);
    }

    @Override
    public NissasJudgment copy() {
        return new NissasJudgment(this);
    }
}

class NissasJudgmentEffect extends OneShotEffect {

    public NissasJudgmentEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose up to one target creature an opponent controls. Each creature you control with a +1/+1 counter on it deals damage equal to its power to that creature";
    }

    public NissasJudgmentEffect(final NissasJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public NissasJudgmentEffect copy() {
        return new NissasJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1, controller.getId(), game)) {
                    if (permanent.getPower().getValue() > 0) {
                        targetCreature.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
