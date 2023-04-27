package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class MinionReflector extends CardImpl {

    public MinionReflector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Whenever a nontoken creature enters the battlefield under your control, you may pay {2}. If you do, create a token that's a copy of that creature. That token has haste and "At the beginning of the end step, sacrifice this permanent."
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(
                        new MinionReflectorEffect(), new ManaCostsImpl<>("{2}"), "Pay {2} " +
                        "to create a token that's a copy of that creature that entered the battlefield?"
                ),
                StaticFilters.FILTER_CREATURE_NON_TOKEN, false, SetTargetPointer.PERMANENT, null
        ));
    }

    private MinionReflector(final MinionReflector card) {
        super(card);
    }

    @Override
    public MinionReflector copy() {
        return new MinionReflector(this);
    }
}


class MinionReflectorEffect extends OneShotEffect {

    public MinionReflectorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature, except it has haste and \"At the beginning of the end step, sacrifice this permanent.\"";
    }

    public MinionReflectorEffect(final MinionReflectorEffect effect) {
        super(effect);
    }

    @Override
    public MinionReflectorEffect copy() {
        return new MinionReflectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanents()) {
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, false), Duration.Custom);
                continuousEffect.setTargetPointer(new FixedTarget(addedToken.getId()));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }

        return false;
    }
}
