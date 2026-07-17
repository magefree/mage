package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JudgmentOfAlexander extends CardImpl {

    public JudgmentOfAlexander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent all damage that would be dealt to you this turn by sources your opponents control. Whenever damage from a creature is prevented this way, each commander creature you control deals damage equal to its power to that creature.
        this.getSpellAbility().addEffect(new JudgmentOfAlexanderPreventionEffect());
    }

    private JudgmentOfAlexander(final JudgmentOfAlexander card) {
        super(card);
    }

    @Override
    public JudgmentOfAlexander copy() {
        return new JudgmentOfAlexander(this);
    }
}

class JudgmentOfAlexanderPreventionEffect extends PreventionEffectImpl {

    JudgmentOfAlexanderPreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "prevent all damage that would be dealt to you this turn by sources your opponents control. " +
                "Whenever damage from a creature is prevented this way, each commander creature you control " +
                "deals damage equal to its power to that creature";
    }

    private JudgmentOfAlexanderPreventionEffect(final JudgmentOfAlexanderPreventionEffect effect) {
        super(effect);
    }

    @Override
    public JudgmentOfAlexanderPreventionEffect copy() {
        return new JudgmentOfAlexanderPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event instanceof DamagePlayerEvent
                && event.getAmount() >= 1
                && source.isControlledBy(event.getTargetId())
                && game.getOpponents(source.getControllerId())
                .contains(game.getControllerId(event.getSourceId()));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData data = preventDamageAction(event, source, game);
        if (data.getPreventedDamage() <1) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null && permanent.isCreature(game)) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(new JudgmentOfAlexanderDamageEffect(permanent, game), false), source);
        }
        return false;
    }
}

class JudgmentOfAlexanderDamageEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    JudgmentOfAlexanderDamageEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        this.setTargetPointer(new FixedTarget(permanent, game));
        staticText = "each commander creature you control deals damage equal to its power to that creature";
    }

    private JudgmentOfAlexanderDamageEffect(final JudgmentOfAlexanderDamageEffect effect) {
        super(effect);
    }

    @Override
    public JudgmentOfAlexanderDamageEffect copy() {
        return new JudgmentOfAlexanderDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        for (Permanent commander : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.damage(commander.getPower().getValue(), commander.getId(), source, game);
        }
        return true;
    }
}
