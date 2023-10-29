package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaheeliTheSunsBrilliance extends CardImpl {

    public SaheeliTheSunsBrilliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}{R}, {T}: Create a token that's a copy of another target creature or artifact you control, except it's an artifact in addition to its other types. It gains haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new SaheeliTheSunsBrillianceEffect(), new ManaCostsImpl<>("{U}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
    }

    private SaheeliTheSunsBrilliance(final SaheeliTheSunsBrilliance card) {
        super(card);
    }

    @Override
    public SaheeliTheSunsBrilliance copy() {
        return new SaheeliTheSunsBrilliance(this);
    }
}

class SaheeliTheSunsBrillianceEffect extends OneShotEffect {

    SaheeliTheSunsBrillianceEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of another target creature or artifact you control, " +
                "except it's an artifact in addition to its other types. It gains haste. " +
                "Sacrifice it at the beginning of the next end step";
    }

    private SaheeliTheSunsBrillianceEffect(final SaheeliTheSunsBrillianceEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliTheSunsBrillianceEffect copy() {
        return new SaheeliTheSunsBrillianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect().setBecomesArtifact(true).setHasHaste(true);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
