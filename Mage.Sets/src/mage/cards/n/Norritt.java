
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author MTGfan & L_J
 */
public final class Norritt extends CardImpl {

    private static final FilterCreaturePermanent filterBlue = new FilterCreaturePermanent("blue creature");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("non-Wall creature");

    static {
        filterCreature.add(Predicates.not(SubType.WALL.getPredicate()));
        filterCreature.add(new ControlledFromStartOfControllerTurnPredicate());
        filterCreature.add(TargetController.ACTIVE.getControllerPredicate());
        filterCreature.setMessage("non-Wall creature the active player has controlled continuously since the beginning of the turn.");
    }

    public Norritt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Untap target blue creature.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        ability1.addTarget(new TargetCreaturePermanent(filterBlue));
        this.addAbility(ability1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only before attackers are declared.
        Ability ability2 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn),
                new TapSourceCost(), BeforeAttackersAreDeclaredCondition.instance,
                "{T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. "
                + "That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. "
                + "Activate only before attackers are declared.");
        ability2.addEffect(new NorrittDelayedDestroyEffect());
        ability2.addTarget(new TargetCreaturePermanent(filterCreature));
        this.addAbility(ability2, new AttackedThisTurnWatcher());

    }

    private Norritt(final Norritt card) {
        super(card);
    }

    @Override
    public Norritt copy() {
        return new Norritt(this);
    }
}

class NorrittDelayedDestroyEffect extends OneShotEffect {

    public NorrittDelayedDestroyEffect() {
        super(Outcome.Detriment);
        this.staticText = "If it doesn't, destroy it at the beginning of the next end step";
    }

    public NorrittDelayedDestroyEffect(final NorrittDelayedDestroyEffect effect) {
        super(effect);
    }

    @Override
    public NorrittDelayedDestroyEffect copy() {
        return new NorrittDelayedDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DestroyTargetEffect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.ANY, new InvertCondition(TargetAttackedThisTurnCondition.instance));
        delayedAbility.getTargets().addAll(source.getTargets());
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
