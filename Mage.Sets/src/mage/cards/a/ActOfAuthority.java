package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ActOfAuthority extends CardImpl {

    public ActOfAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // When Act of Authority enters the battlefield, you may exile target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
        // At the beginning of your upkeep, you may exile target artifact or enchantment. If you do, its controller gains control of Act of Authority.
        ability = new BeginningOfUpkeepTriggeredAbility(new ActOfAuthorityEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private ActOfAuthority(final ActOfAuthority card) {
        super(card);
    }

    @Override
    public ActOfAuthority copy() {
        return new ActOfAuthority(this);
    }
}

class ActOfAuthorityEffect extends OneShotEffect {

    public ActOfAuthorityEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile target artifact or enchantment. If you do, its controller gains control of {this}";
    }

    public ActOfAuthorityEffect(final ActOfAuthorityEffect effect) {
        super(effect);
    }

    @Override
    public ActOfAuthorityEffect copy() {
        return new ActOfAuthorityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent != null && new ExileTargetEffect().apply(game, source)) {
            Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
            if (sourcePermanent != null) {
                ContinuousEffect effect = new ActOfAuthorityGainControlEffect(Duration.Custom, targetPermanent.getControllerId());
                effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class ActOfAuthorityGainControlEffect extends ContinuousEffectImpl {

    UUID controller;

    public ActOfAuthorityGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public ActOfAuthorityGainControlEffect(final ActOfAuthorityGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public ActOfAuthorityGainControlEffect copy() {
        return new ActOfAuthorityGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null) {
            return permanent.changeControllerId(controller, game, source);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of {this}";
    }
}
