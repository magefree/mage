package mage.cards.l;

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
import mage.constants.*;
import mage.counters.BoostCounter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class LesserWerewolf extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature blocking or blocked by {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public LesserWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {B}: If Lesser Werewolf’s power is 1 or more, it gets -1/-0 until end of turn and put a -0/-1 counter on target creature blocking or blocked by Lesser Werewolf. Activate this ability only during the declare blockers step.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new LesserWerewolfEffect(), new ManaCostsImpl<>("{B}"),
                new IsStepCondition(PhaseStep.DECLARE_BLOCKERS, false)
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private LesserWerewolf(final LesserWerewolf card) {
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
        this.staticText = "If {this}'s power is 1 or more, it gets -1/-0 until end of turn and put a -0/-1 counter on target creature blocking or blocked by {this}";
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
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source)); // must be valid target
        if (controller == null || sourcePermanent == null || targetPermanent == null) {
            return false;
        }
        if (sourcePermanent.getPower().getValue() >= 1) {
            game.addEffect(new BoostSourceEffect(-1, 0, Duration.EndOfTurn), source);
            new AddCountersTargetEffect(new BoostCounter(0, -1), outcome).apply(game, source);
        }
        return true;
    }
}
