
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class LesserWerewolf extends CardImpl {

    public LesserWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {B}: If Lesser Werewolf’s power is 1 or more, it gets -1/-0 until end of turn and put a -0/-1 counter on target creature blocking or blocked by Lesser Werewolf. Activate this ability only during the declare blockers step.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new LesserWerewolfEffect(), new ManaCostsImpl("{B}"), new IsStepCondition(PhaseStep.DECLARE_BLOCKERS, false));
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by Lesser Werewolf");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public LesserWerewolf(final LesserWerewolf card) {
        super(card);
    }

    @Override
    public LesserWerewolf copy() {
        return new LesserWerewolf(this);
    }
}

class LesserWerewolfEffect extends OneShotEffect {

    public LesserWerewolfEffect() {
        super(Outcome.Detriment);
        this.staticText = "If {this}’s power is 1 or more, it gets -1/-0 until end of turn and put a -0/-1 counter on target creature blocking or blocked by {this}";
    }

    public LesserWerewolfEffect(final LesserWerewolfEffect effect) {
        super(effect);
    }

    @Override
    public LesserWerewolfEffect copy() {
        return new LesserWerewolfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent targetPermanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (controller != null && sourcePermanent != null && targetPermanent != null) {
            if (sourcePermanent.getPower().getValue() >= 1) {
                game.addEffect(new BoostSourceEffect(-1, 0, Duration.EndOfTurn), source);
                new AddCountersTargetEffect(new BoostCounter(0, -1), outcome).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
