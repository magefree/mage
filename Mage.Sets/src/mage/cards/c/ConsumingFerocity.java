
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ConsumingFerocity extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.WALL)));
    }

    public ConsumingFerocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant non-Wall creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 0, Duration.WhileOnBattlefield)));

        // At the beginning of your upkeep, put a +1/+0 counter on enchanted creature. If that creature has three or more +1/+0 counters on it, it deals damage equal to its power to its controller, then destroy that creature and it can't be regenerated.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ConsumingFerocityEffect(), TargetController.YOU, false));
    }

    public ConsumingFerocity(final ConsumingFerocity card) {
        super(card);
    }

    @Override
    public ConsumingFerocity copy() {
        return new ConsumingFerocity(this);
    }
}

class ConsumingFerocityEffect extends OneShotEffect {

    public ConsumingFerocityEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+0 counter on enchanted creature. If that creature has three or more +1/+0 counters on it, it deals damage equal to its power to its controller, then destroy that creature and it can't be regenerated";
    }

    public ConsumingFerocityEffect(final ConsumingFerocityEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingFerocityEffect copy() {
        return new ConsumingFerocityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            Effect effect = new AddCountersAttachedEffect(CounterType.P1P0.createInstance(), "enchanted creature");
            effect.apply(game, source);
            if (creature.getCounters(game).getCount(CounterType.P1P0) > 2) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    player.damage(creature.getPower().getValue(), creature.getId(), game, false, true);
                }
                effect = new DestroyTargetEffect(true);
                effect.setTargetPointer(new FixedTarget(creature, game));
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
