package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenDuplication extends CardImpl {

    public MoltenDuplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Create a token that's a copy of target artifact or creature you control, except it's an artifact in addition to its other types. It gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new MoltenDuplicationEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private MoltenDuplication(final MoltenDuplication card) {
        super(card);
    }

    @Override
    public MoltenDuplication copy() {
        return new MoltenDuplication(this);
    }
}

class MoltenDuplicationEffect extends OneShotEffect {

    MoltenDuplicationEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target artifact or creature you control, " +
                "except it's an artifact in addition to its other types. " +
                "It gains haste until end of turn. Sacrifice it at the beginning of the next end step";
    }

    private MoltenDuplicationEffect(final MoltenDuplicationEffect effect) {
        super(effect);
    }

    @Override
    public MoltenDuplicationEffect copy() {
        return new MoltenDuplicationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, CardType.ARTIFACT, false
        );
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
