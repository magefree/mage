package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DevastatingOnslaught extends CardImpl {

    public DevastatingOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Create X tokens that are copies of target artifact or creature you control. Those tokens gain haste until end of turn. Sacrifice them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new DevastatingOnslaughtEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private DevastatingOnslaught(final DevastatingOnslaught card) {
        super(card);
    }

    @Override
    public DevastatingOnslaught copy() {
        return new DevastatingOnslaught(this);
    }
}

class DevastatingOnslaughtEffect extends OneShotEffect {

    DevastatingOnslaughtEffect() {
        super(Outcome.Benefit);
        staticText = "create X tokens that are copies of target artifact or creature you control. " +
                "Those tokens gain haste until end of turn. Sacrifice them at the beginning of the next end step";
    }

    private DevastatingOnslaughtEffect(final DevastatingOnslaughtEffect effect) {
        super(effect);
    }

    @Override
    public DevastatingOnslaughtEffect copy() {
        return new DevastatingOnslaughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        int xValue = GetXValue.instance.calculate(game, source, this);
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true, xValue);
        effect.setSavedPermanent(permanent);
        if (xValue > 0) {
            effect.apply(game, source);
        }
        // CR 603.7a: the "sacrifice them" delayed trigger is created by the resolution whether or not
        // any token was created. With X = 0 it triggers at the beginning of the next end step and
        // sacrifices nothing (CR 603.7c).
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
