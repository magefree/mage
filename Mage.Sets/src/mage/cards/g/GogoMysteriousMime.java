package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;
import mage.util.functions.CopyApplier;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GogoMysteriousMime extends CardImpl {

    public GogoMysteriousMime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may have Gogo become a copy of another target creature you control until end of turn, except its name is Gogo, Mysterious Mime. If you do, Gogo and that creature each get +2/+0 and gain haste until end of turn and attack this turn if able.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GogoMysteriousMimeEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private GogoMysteriousMime(final GogoMysteriousMime card) {
        super(card);
    }

    @Override
    public GogoMysteriousMime copy() {
        return new GogoMysteriousMime(this);
    }
}

class GogoMysteriousMimeEffect extends OneShotEffect {

    private static final CopyApplier applier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
            blueprint.setName("Gogo, Mysterious Mime");
            return true;
        }
    };

    GogoMysteriousMimeEffect() {
        super(Outcome.Benefit);
        staticText = "have {this} become a copy of another target creature you control until end of turn, " +
                "except its name is Gogo, Mysterious Mime. If you do, {this} and that creature " +
                "each get +2/+0 and gain haste until end of turn and attack this turn if able";
    }

    private GogoMysteriousMimeEffect(final GogoMysteriousMimeEffect effect) {
        super(effect);
    }

    @Override
    public GogoMysteriousMimeEffect copy() {
        return new GogoMysteriousMimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        game.copyPermanent(Duration.EndOfTurn, permanent, sourcePermanent.getId(), source, applier);
        TargetPointer targetPointer = new FixedTargets(Arrays.asList(sourcePermanent, permanent), game);
        game.addEffect(new BoostTargetEffect(2, 0)
                .setTargetPointer(targetPointer.copy()), source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(targetPointer.copy()), source);
        game.addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn)
                .setTargetPointer(targetPointer.copy()), source);
        return true;
    }
}
