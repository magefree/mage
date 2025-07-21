package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 * @author Quercitron
 */
public final class MightMakesRight extends CardImpl {

    public MightMakesRight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, gain control
        // of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn))
                .withInterveningIf(ControlsEachCreatureWithGreatestPowerCondition.instance);
        ability.addEffect(new UntapTargetEffect("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private MightMakesRight(final MightMakesRight card) {
        super(card);
    }

    @Override
    public MightMakesRight copy() {
        return new MightMakesRight(this);
    }
}

enum ControlsEachCreatureWithGreatestPowerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game
        );
        if (permanents.isEmpty()) {
            return false;
        }
        int max = permanents
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(Integer.MIN_VALUE);
        return permanents
                .stream()
                .filter(permanent -> permanent.getPower().getValue() >= max)
                .map(Controllable::getControllerId)
                .allMatch(source::isControlledBy);
    }

    @Override
    public String toString() {
        return "you control each creature on the battlefield with the greatest power";
    }
}
