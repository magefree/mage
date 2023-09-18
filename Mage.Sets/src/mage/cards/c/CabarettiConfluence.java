package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabarettiConfluence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public CabarettiConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{G}{W}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Create a token that's a copy of target creature you control. It gains haste. Sacrifice it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new CabarettiConfluenceEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // • Exile target artifact or enchantment.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT)));

        // • Creatures target player controls gets +1/+1 and gain first strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostAllEffect(
                1, 1, Duration.EndOfTurn, filter, false
        ).setText("creatures target player controls get +1/+1")).addEffect(new GainAbilityAllEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and gain first strike until end of turn")));
    }

    private CabarettiConfluence(final CabarettiConfluence card) {
        super(card);
    }

    @Override
    public CabarettiConfluence copy() {
        return new CabarettiConfluence(this);
    }
}

class CabarettiConfluenceEffect extends OneShotEffect {

    CabarettiConfluenceEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target creature you control. " +
                "It gains haste. Sacrifice it at the beginning of the next end step";
    }

    private CabarettiConfluenceEffect(final CabarettiConfluenceEffect effect) {
        super(effect);
    }

    @Override
    public CabarettiConfluenceEffect copy() {
        return new CabarettiConfluenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.addAdditionalAbilities(HasteAbility.getInstance());
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
