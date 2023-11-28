package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BranchingEvolution extends CardImpl {

    public BranchingEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // If one or more +1/+1 counters would be put a on a creature you control, twice that many +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(new BranchingEvolutionEffect()));
    }

    private BranchingEvolution(final BranchingEvolution card) {
        super(card);
    }

    @Override
    public BranchingEvolution copy() {
        return new BranchingEvolution(this);
    }
}

class BranchingEvolutionEffect extends ReplacementEffectImpl {

    BranchingEvolutionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature you control, " +
                "twice that many +1/+1 counters are put on that creature instead";
    }

    private BranchingEvolutionEffect(final BranchingEvolutionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.isControlledBy(source.getControllerId())
                    && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public BranchingEvolutionEffect copy() {
        return new BranchingEvolutionEffect(this);
    }
}
