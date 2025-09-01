package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.condition.common.TargetAttackedThisTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author MTGfan & L_J
 */
public final class Norritt extends CardImpl {

    private static final FilterCreaturePermanent filterBlue = new FilterCreaturePermanent("blue creature");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("non-Wall creature the active player has controlled continuously since the beginning of the turn");

    static {
        filterCreature.add(Predicates.not(SubType.WALL.getPredicate()));
        filterCreature.add(new ControlledFromStartOfControllerTurnPredicate());
        filterCreature.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public Norritt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.IMP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Untap target blue creature.
        Ability ability1 = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability1.addTarget(new TargetPermanent(filterBlue));
        this.addAbility(ability1);

        // {T}: Choose target non-Wall creature the active player has controlled continuously since the beginning of the turn. That creature attacks this turn if able. If it doesn't, destroy it at the beginning of the next end step. Activate this ability only before attackers are declared.
        Ability ability2 = new ActivateIfConditionActivatedAbility(
                new AttacksIfAbleTargetEffect(Duration.EndOfTurn)
                        .setText("choose target non-Wall creature the active player has controlled continuously " +
                                "since the beginning of the turn. That creature attacks this turn if able"),
                new TapSourceCost(), BeforeAttackersAreDeclaredCondition.instance
        );
        ability2.addEffect(new NorrittDelayedDestroyEffect());
        ability2.addTarget(new TargetPermanent(filterCreature));
        this.addAbility(ability2);

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

    NorrittDelayedDestroyEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy it at the beginning of the next end step if it didn't attack this turn";
    }

    private NorrittDelayedDestroyEffect(final NorrittDelayedDestroyEffect effect) {
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
