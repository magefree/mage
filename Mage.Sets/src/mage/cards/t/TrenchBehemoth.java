package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrenchBehemoth extends CardImpl {

    public TrenchBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Return a land you control to its owner's hand: Untap Trench Behemoth. It gains hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new UntapSourceEffect(), new ReturnToHandChosenControlledPermanentCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND)
        ));
        ability.addEffect(new GainAbilitySourceEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains hexproof until end of turn"));
        this.addAbility(ability);

        // Whenever a land enters the battlefield under your control, target creature an opponent controls attacks during its controller's next combat phase if able.
        ability = new LandfallAbility(new TrenchBehemothEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private TrenchBehemoth(final TrenchBehemoth card) {
        super(card);
    }

    @Override
    public TrenchBehemoth copy() {
        return new TrenchBehemoth(this);
    }
}

class TrenchBehemothEffect extends RequirementEffect {

    public TrenchBehemothEffect() {
        super(Duration.Custom);
        staticText = "target creature an opponent controls attacks during its controller's next combat phase if able";
    }

    public TrenchBehemothEffect(final TrenchBehemothEffect effect) {
        super(effect);
    }

    @Override
    public TrenchBehemothEffect copy() {
        return new TrenchBehemothEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return getTargetPointer().getFirst(game, source).equals(permanent.getId());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPermanent(getTargetPointer().getFirst(game, source)) == null) {
            return true;
        }
        return game.isActivePlayer(game.getControllerId(getTargetPointer().getFirst(game, source)))
                && game.getTurnPhaseType() == TurnPhase.COMBAT
                && game.getTurnStepType() == PhaseStep.END_COMBAT;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
