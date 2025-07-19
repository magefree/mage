package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RequiemMonolith extends CardImpl {

    public RequiemMonolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        // {T}: Until end of turn, target creature gains "Whenever this creature is dealt damage, you draw that many cards and lose that much life." That creature's controller may have this artifact deal 1 damage to it. Activate only as a sorcery.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY, true), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(SavedDamageValue.MUCH).setText("and lose that much life"));
        ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(ability)
                .setText("Until end of turn, target creature gains \"Whenever this creature " +
                        "is dealt damage, you draw that many cards and lose that much life.\""), new TapSourceCost());
        ability.addEffect(new RequiemMonolithEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RequiemMonolith(final RequiemMonolith card) {
        super(card);
    }

    @Override
    public RequiemMonolith copy() {
        return new RequiemMonolith(this);
    }
}

class RequiemMonolithEffect extends OneShotEffect {

    RequiemMonolithEffect() {
        super(Outcome.Benefit);
        staticText = "That creature's controller may have {this} deal 1 damage to it";
    }

    private RequiemMonolithEffect(final RequiemMonolithEffect effect) {
        super(effect);
    }

    @Override
    public RequiemMonolithEffect copy() {
        return new RequiemMonolithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null
                && Optional
                .ofNullable(permanent)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.chooseUse(
                        Outcome.Neutral, "Have this artifact deal 1 damage to " +
                                permanent.getLogName() + '?', source, game
                ))
                .filter(x -> permanent.damage(1, source, game) > 0)
                .isPresent();
    }
}
